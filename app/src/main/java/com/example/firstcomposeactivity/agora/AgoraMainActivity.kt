package com.example.firstcomposeactivity.agora

/*import io.agora.rtc.Constants
import io.agora.rtc.Constants.RELAY_STATE_CONNECTING
import io.agora.rtc.Constants.RELAY_STATE_FAILURE
import io.agora.rtc.Constants.RELAY_STATE_IDLE
import io.agora.rtc.Constants.RELAY_STATE_RUNNING
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.ScreenCaptureParameters
import io.agora.rtc.models.ChannelMediaOptions
import io.agora.rtc.models.ClientRoleOptions
import io.agora.rtc.video.ChannelMediaInfo
import io.agora.rtc.video.ChannelMediaRelayConfiguration
import io.agora.rtc.video.VideoCanvas*/


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firstcomposeactivity.R
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.ClientRoleOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.RELAY_STATE_CONNECTING
import io.agora.rtc2.Constants.RELAY_STATE_FAILURE
import io.agora.rtc2.Constants.RELAY_STATE_IDLE
import io.agora.rtc2.Constants.RELAY_STATE_RUNNING
import io.agora.rtc2.Constants.VideoSourceType
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.RtcEngineEx
import io.agora.rtc2.ScreenCaptureParameters
import io.agora.rtc2.video.ChannelMediaInfo
import io.agora.rtc2.video.ChannelMediaRelayConfiguration
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration
import io.agora.rtc2.video.VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15
import io.agora.rtc2.video.VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE

class AgoraMainActivity : AppCompatActivity() {


    //screen sharing

    // Volume Control
    private var volumeSeekBar: SeekBar? = null
    private var muteCheckBox: CheckBox? = null
    private var volume = 50
    private var remoteUid = 0 // Stores the uid of the remote user


    // Screen sharing
    private val DEFAULT_SHARE_FRAME_RATE = 10
    private var isSharingScreen = false
    private var fgServiceIntent: Intent? = null

    private val screenCaptureParameters = ScreenCaptureParameters()


    private fun joinScreenShareChannel(channelId: String) {
        rtcEngine.setParameters("{\"che.video.mobile_1080p\":true}")
        rtcEngine.setParameters("{"
                + "\"rtc.report_app_scenario\":"
                + "{"
                + "\"appScenario\":" + 100 + ","
                + "\"serviceType\":" + 11 + ","
                + "\"appVersion\":\"" + RtcEngine.getSdkVersion() + "\""
                + "}"
                + "}")
        rtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        /**Enable video module */
        rtcEngine.enableVideo()
        // Setup video encoding configs
        rtcEngine.setVideoEncoderConfiguration(VideoEncoderConfiguration(
            VideoEncoderConfiguration.VD_640x360, FRAME_RATE_FPS_15,
            VideoEncoderConfiguration.STANDARD_BITRATE, ORIENTATION_MODE_ADAPTIVE))
        /**Set up to play remote sound with receiver */
        rtcEngine.setDefaultAudioRoutetoSpeakerphone(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
           this.startForegroundService(fgServiceIntent)
        }
        val metrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics)
        screenCaptureParameters.captureVideo = true
        screenCaptureParameters.videoCaptureParameters.width = 720
        screenCaptureParameters.videoCaptureParameters.height =
            (720 * 1.0f / metrics.widthPixels * metrics.heightPixels).toInt()
        screenCaptureParameters.videoCaptureParameters.framerate =
            DEFAULT_SHARE_FRAME_RATE
        screenCaptureParameters.captureAudio = true
        screenCaptureParameters.audioCaptureParameters.captureSignalVolume =
            100
        rtcEngine.startScreenCapture(screenCaptureParameters)
            startScreenSharePreview()
        /**Please configure accessToken in the string_config file.
         * A temporary token generated in Console. A temporary token is valid for 24 hours. For details, see
         * https://docs.agora.io/en/Agora%20Platform/token?platform=All%20Platforms#get-a-temporary-token
         * A token generated at the server. This applies to scenarios with high-security requirements. For details, see
         * https://docs.agora.io/en/cloud-recording/token_server_java?platform=Java */
        token.let {
            /** Allows a user to join a channel.
             * if you do not specify the uid, we will generate the uid for you */
            // set options
            val options = ChannelMediaOptions()
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            options.autoSubscribeVideo = true
            options.autoSubscribeAudio = true
            options.publishCameraTrack = false
            options.publishMicrophoneTrack = false
            options.publishScreenCaptureVideo = true
            options.publishScreenCaptureAudio = true
            val res: Int = rtcEngine.joinChannel(token, channelId, 0, options)
            if (res != 0) {
                // Usually happens with invalid parameters
                // Error code description can be found at:
                // en: https://docs.agora.io/en/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html
                // cn: https://docs.agora.io/cn/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html

            }
            // Prevent repeated entry
        }
    }


    //Agora relay for creator battle
    val PERMISSION_REQ_ID = 22

    val REQUESTED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    var role = Constants.CLIENT_ROLE_AUDIENCE

    val appId = "570ba303821d4e59bbe4e2ea0e244ad9";

    val appCertificate = "804315c62a1e4420b283fe87d6970e1a"

    // Fill the channel name.
    val channelName = "ganesh";

    // Fill the temp token generated on Agora Console.
    val token =
        "007eJxTYOCyuvfAr37iSe+UXYGSqpvunOzy3zN1vq1IcF/rHMc97IIKDKbmBkmJxgbGFkaGKSapppZJSakmqUapiQapRiYmiSmWzBNKkhsCGRmmPd/LwsgAgSA+G0N6Yl5qcQYDAwBKRB+Y";

    val token2 =
        "006570ba303821d4e59bbe4e2ea0e244ad9IAACbqGc1dC8s8D1towcKwULdI8BzFSA5YjhGjNRa99ZeXEqSRIAAAAAEABiLYCEur70YgEAAQC6vvRi"

    lateinit var channel1: EditText
    lateinit var channel2: EditText
    lateinit var ShareScreenButton: Button

    lateinit var rtcEngine: RtcEngine

    val rtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)


            //ss
            remoteUid = uid



            Log.d("Ganesh", " remote joined uid of host - ${uid}")

            runOnUiThread {
                setUpRemoteView(uid)
            }
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)

            Log.d("Ganesh", " onLeaveChannel - ${stats}")
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Log.d("Ganesh",
                " onJoinChannelSuccess chammel- ${channel}, uid {$uid}, elapsed - $elapsed")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            Log.d("Ganesh", " onUserOffline - uid {$uid}")

        }

        override fun onChannelMediaRelayStateChanged(state: Int, code: Int) {
            super.onChannelMediaRelayStateChanged(state, code)

            when (state) {
                RELAY_STATE_CONNECTING -> {

                    Log.d("Ganesh",
                        " onChannelMediaRelayStateChanged - connecting state {$state}, code - $code")

                }

                RELAY_STATE_RUNNING -> {
                    Log.d("Ganesh",
                        " onChannelMediaRelayStateChanged - RELAY_STATE_RUNNING state {$state}, code - $code")

                }
                RELAY_STATE_FAILURE -> {
                    Log.d("Ganesh",
                        " onChannelMediaRelayStateChanged - fail state {$state}, code - $code")

                }
                RELAY_STATE_IDLE -> {
                    Log.d("Ganesh",
                        " onChannelMediaRelayStateChanged - RELAY_STATE_IDLE state {$state}, code - $code")
                }
            }
        }

        override fun onChannelMediaRelayEvent(code: Int) {
            super.onChannelMediaRelayEvent(code)
            Log.d("Ganesh", " onChannelMediaRelay Event callback-   code - $code")

        }

        override fun onError(err: Int) {
            Log.e(
                "Ganesh",
                String.format("onError code %d message %s", err, RtcEngine.getErrorDescription(err))
            )
        }
    }

    private fun setUpRemoteView(uid: Int) {

        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)

        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)


        container.addView(surfaceView)

        rtcEngine.setupRemoteVideo(
            VideoCanvas(
                surfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.agora_main_relay)


        channel1 = findViewById<EditText>(R.id.channel1)
        channel2 = findViewById<EditText>(R.id.channel2)
        ShareScreenButton = findViewById(R.id.ShareScreenButton)


        val roleChange = findViewById<Button>(R.id.role)

        val joinChannel = findViewById<Button>(R.id.joinChannel)
        val joinAnotherChannel = findViewById<Button>(R.id.joinAnotherChannel)
        val relay = findViewById<Button>(R.id.relay)


        roleChange.setOnClickListener {
            if (role == Constants.CLIENT_ROLE_AUDIENCE) {
                role = Constants.CLIENT_ROLE_BROADCASTER
                joinChannel.text = "Join as host"
                joinAnotherChannel.text = "Join as host 2"
            } else {
                role = Constants.CLIENT_ROLE_AUDIENCE
                joinChannel.text = "Join as audience"
                joinAnotherChannel.text = "Join as audiece 2"
            }

        }




        joinChannel.setOnClickListener {
            joinChannel(1)
        }

        joinAnotherChannel.setOnClickListener {
            joinChannel(2)
        }


        relay.setOnClickListener {
            joinAnotherChannelAsHost()
        }
//        joinChannel()


//setContent {
//            FirstComposeActivityTheme {
//
//            }
//        }


        ShareScreenButton.setOnClickListener {
//            shareScreen(ShareScreenButton)
            joinScreenShareChannel(channel1.text.toString())
        }

        //ss

        volumeSeekBar = findViewById<View>(R.id.volumeSeekBar) as SeekBar
        volumeSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                volume = progress
                rtcEngine.adjustRecordingSignalVolume(volume)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Required to implement OnSeekBarChangeListener
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //Required to implement OnSeekBarChangeListener
            }
        })

        muteCheckBox = findViewById<View>(R.id.muteCheckBox) as CheckBox
        muteCheckBox!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            rtcEngine.muteRemoteAudioStream(remoteUid,
                isChecked)
        })



        startShareService()


    }

    private fun startShareService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            fgServiceIntent = Intent(this,
                MediaProjectFgService::class.java)
        }
    }


    fun shareScreen(view: View) {
        val sharingButton = view as Button
        if (!isSharingScreen) { // Start sharing
            // Ensure that your Android version is Lollipop or higher.
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    fgServiceIntent = Intent(this, AgoraMainActivity::class.java)
                    startForegroundService(fgServiceIntent)
                }
                // Get the screen metrics
                val metrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(metrics)

                // Set screen capture parameters
                val screenCaptureParameters = ScreenCaptureParameters()
                screenCaptureParameters.captureVideo = true
                screenCaptureParameters.videoCaptureParameters.width = metrics.widthPixels
                screenCaptureParameters.videoCaptureParameters.height = metrics.heightPixels
                screenCaptureParameters.videoCaptureParameters.framerate = DEFAULT_SHARE_FRAME_RATE
                screenCaptureParameters.captureAudio = true
                screenCaptureParameters.audioCaptureParameters.captureSignalVolume = 50

                // Start screen sharing
                rtcEngine?.startScreenCapture(screenCaptureParameters)
                isSharingScreen = true
                startScreenSharePreview()

                // Update channel media options to publish the screen sharing video stream
                updateMediaPublishOptions(true)
                sharingButton.text = "Stop Screen Sharing"
            }
        } else { // Stop sharing
            rtcEngine.stopScreenCapture()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (fgServiceIntent != null) stopService(fgServiceIntent)
            }
            isSharingScreen = false
            sharingButton.text = "Start Screen Sharing"

            // Restore camera and microphone publishing
            updateMediaPublishOptions(false)
//            setupLocalVideo()
        }
    }

    private fun startScreenSharePreview() {

        // Create render view by RtcEngine

        val container = findViewById<FrameLayout>(R.id.local_video_view_container)


        val surfaceView = SurfaceView(this)
        if (container!!.childCount > 0) {
            container!!.removeAllViews()
        }
        // Add to the local container
        container!!.addView(surfaceView,
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
        // Setup local video to render your local camera preview
        rtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, Constants.RENDER_MODE_FIT,
            Constants.VIDEO_MIRROR_MODE_DISABLED,
            Constants.VIDEO_SOURCE_SCREEN_PRIMARY,
            0))
        rtcEngine!!.startPreview(VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY)



/*
        // Create render view by RtcEngine
        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
        val surfaceView = SurfaceView(baseContext)
        if (container.childCount > 0) {
            container.removeAllViews()
        }
        // Add to the local container
        container.addView(surfaceView,
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
        // Setup local video to render your local camera preview
        rtcEngine.setupLocalVideo(VideoCanvas(surfaceView, Constants.RENDER_MODE_FIT,
            Constants.VIDEO_MIRROR_MODE_DISABLED,
            Constants.VIDEO_SOURCE_SCREEN_PRIMARY,
            0))
        rtcEngine.startPreview(Constants.VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY)*/
    }

    fun updateMediaPublishOptions(publishScreen: Boolean) {
        val mediaOptions = ChannelMediaOptions()
        mediaOptions.publishCameraTrack = !publishScreen
        mediaOptions.publishMicrophoneTrack = !publishScreen
        mediaOptions.publishScreenCaptureVideo = publishScreen
        mediaOptions.publishScreenCaptureAudio = publishScreen
        rtcEngine.updateChannelMediaOptions(mediaOptions)
    }

    private fun joinChannel(joinChannel: Int) {
        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
            checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)
        ) {
            initializeAndJoinChannel(joinChannel);
        }
    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode)
            return false
        }
        return true
    }

    private fun initializeAndJoinChannel(joinChannel: Int) {
        try {
            rtcEngine = RtcEngine.create(baseContext, appId, rtcEventHandler)
        } catch (e: Exception) {
            throw RuntimeException("Check error $e")
        }

        rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        val clientRoleOptions = ClientRoleOptions()
        clientRoleOptions.audienceLatencyLevel = Constants.AUDIENCE_LATENCY_LEVEL_LOW_LATENCY



        rtcEngine.apply {
            setClientRole(
Constants.CLIENT_ROLE_BROADCASTER
                /*role*/, clientRoleOptions)
            enableVideo()

        }


        val container = findViewById<FrameLayout>(R.id.local_video_view_container)

        val surfaceView = RtcEngine.CreateRendererView(baseContext)

        container.addView(surfaceView)


        rtcEngine.setupLocalVideo(
            VideoCanvas(
                surfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                if (joinChannel == 1) {
                    0
                } else {
                    0
                }
            )
        )

        val channel1 = channel1.text
        val channel2 = channel2.text

        val option = ChannelMediaOptions()
        option.autoSubscribeAudio = true
        option.autoSubscribeVideo = true

        if (joinChannel == 1) {
            rtcEngine.joinChannel(
                token,
                this.channelName,/*channel1.toString()*/ "", 0, /*option*/
            )
        } else {

            rtcEngine.joinChannel(
                token2,
                this.channelName,
/*channel2.toString()*/
                "", 0, /*option*/
            )

//            joinAnotherChannelAsHost()
        }


    }


    fun joinAnotherChannelAsHost() {
//        val srcChannelInfo = ChannelMediaInfo(channel2.text.toString(), token2, 0)
        val srcChannelInfo = ChannelMediaInfo(null, token2, 0)
        val mediaRelayConfiguration = ChannelMediaRelayConfiguration()
        mediaRelayConfiguration.setSrcChannelInfo(srcChannelInfo)
//        val destChannelInfo = ChannelMediaInfo(channel1.text.toString(), token, 0)
        val destChannelInfo = ChannelMediaInfo(channel1.text.toString(), token, 0)
        mediaRelayConfiguration.setDestChannelInfo(channel1.text.toString(), destChannelInfo)
        rtcEngine.startChannelMediaRelay(mediaRelayConfiguration)
//et_channel_ex.setEnabled(false)
//        join_ex.setEnabled(false)

    }


    override fun onDestroy() {
        super.onDestroy()
        rtcEngine.leaveChannel()
        rtcEngine.removeHandler(rtcEventHandler)

val channelMediaRelayConfiguration = ChannelMediaRelayConfiguration()
        rtcEngine.startChannelMediaRelay(ChannelMediaRelayConfiguration())

    }


    class MediaProjectFgService : Service() {
        override fun onBind(intent: Intent): IBinder? {
            return null
        }

        override fun onCreate() {
            super.onCreate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                createNotificationChannel()
            }
        }

        override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
            return START_NOT_STICKY
        }

        override fun onDestroy() {
            super.onDestroy()
            stopForeground(true)
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun createNotificationChannel() {
            val name: CharSequence = getString(R.string.app_name)
            val description = "Notice that we are trying to capture the screen!!"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channelId = "agora_channel_mediaproject"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val notifyId = 1
            // Create a notification and set the notification channel.
            val notification = NotificationCompat.Builder(this, channelId)
                .setContentText(name.toString() + "正在录制屏幕内容...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setChannelId(channelId)
                .setWhen(System.currentTimeMillis())
                .build()
            startForeground(notifyId, notification)
        }
    }



}



/*
class AgoraMainActivity : AppCompatActivity(),View.OnClickListener,
CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private val PROJECTION_REQ_CODE = 1 shl 2
    private val DEFAULT_SHARE_FRAME_RATE = 15
    private var fl_local: FrameLayout? = null
    private  var fl_remote:FrameLayout? = null
    private var join: Button? = null
    private var screenAudio: Switch? = null
    private  var screenPreview:android.widget.Switch? = null
    private var screenAudioVolume: SeekBar? = null
    private var et_channel: EditText? = null
    private var myUid = 0
    private  var remoteUid:kotlin.Int = -1
    private var joined = false
    private var engine: RtcEngineEx? = null
    private val screenCaptureParameters = ScreenCaptureParameters()

    private var fgServiceIntent: Intent? = null

    val TAG = "AgoraMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.agora_main_activity)

        join = findViewById(R.id.btn_join)
        et_channel = findViewById(R.id.et_channel)
        fl_local = findViewById(R.id.fl_camera)
        fl_remote = findViewById<FrameLayout>(R.id.fl_screenshare)
        join?.setOnClickListener(this)
        screenPreview = findViewById<Switch>(R.id.screen_preview)
        screenAudio = findViewById(R.id.screen_audio)
        screenAudioVolume = findViewById(R.id.screen_audio_volume)
        screenPreview?.setOnCheckedChangeListener(this)
        screenAudio?.setOnCheckedChangeListener(this)
        screenAudioVolume?.setOnSeekBarChangeListener(this)


        */
/*join = findViewById(R.id.btn_join)
        et_channel = findViewById(R.id.et_channel)
        fl_local = findViewById(R.id.fl_camera)
        fl_remote = findViewById<FrameLayout>(R.id.fl_screenshare)
        join.setOnClickListener(this)
        screenPreview = findViewById<Switch>(R.id.screen_preview)
        screenAudio = findViewById(R.id.screen_audio)
        screenAudioVolume = findViewById(R.id.screen_audio_volume)
        screenPreview.setOnCheckedChangeListener(this)
        screenAudio.setOnCheckedChangeListener(this)
        screenAudioVolume.setOnSeekBarChangeListener(this)*//*



        // Check if the context is valid
        val context: Context = this ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            fgServiceIntent = Intent(this, MediaProjectFgService::class.java)
        }
        try {
            val config = RtcEngineConfig()
            */
/**
             * The context of Android Activity
             *//*

            config.mContext = context.getApplicationContext()
            */
/**
             * The App ID issued to you by Agora. See [ How to get the App ID](https://docs.agora.io/en/Agora%20Platform/token#get-an-app-id)
             *//*


            val appId = "570ba303821d4e59bbe4e2ea0e244ad9";
            config.mAppId = appId
            */
/** Sets the channel profile of the Agora RtcEngine.
             * CHANNEL_PROFILE_COMMUNICATION(0): (Default) The Communication profile.
             * Use this profile in one-on-one calls or group calls, where all users can talk freely.
             * CHANNEL_PROFILE_LIVE_BROADCASTING(1): The Live-Broadcast profile. Users in a live-broadcast
             * channel have a role as either broadcaster or audience. A broadcaster can both send and receive streams;
             * an audience can only receive streams. *//*

            config.mChannelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
            */
/**
             * IRtcEngineEventHandler is an abstract class providing default implementation.
             * The SDK uses this class to report to the app on SDK runtime events.
             *//*

            config.mEventHandler = iRtcEngineEventHandler
            config.mAudioScenario =
                Constants.AudioScenario.getValue(Constants.AudioScenario.DEFAULT)
            config.mAreaCode =
                getGlobalSettings()?.areaCode!!
            engine = RtcEngine.create(config) as RtcEngineEx
            */
/**
             * This parameter is for reporting the usages of APIExample to agora background.
             * Generally, it is not necessary for you to set this parameter.
             *//*

            engine!!.setParameters("{"
                    + "\"rtc.report_app_scenario\":"
                    + "{"
                    + "\"appScenario\":" + 100 + ","
                    + "\"serviceType\":" + 11 + ","
                    + "\"appVersion\":\"" + RtcEngine.getSdkVersion() + "\""
                    + "}"
                    + "}")
        } catch (e: Exception) {
            e.printStackTrace()
            this.onBackPressed()
        }


    }

    private var globalSettings: GlobalSettings? = null

    fun getGlobalSettings(): GlobalSettings? {
        if (globalSettings == null) {
            globalSettings = GlobalSettings()
        }
        return globalSettings
    }

    */
/*fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*//*


    */
/*fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }*//*


    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.stopService(fgServiceIntent)
        }
        */
/**leaveChannel and Destroy the RtcEngine instance *//*

        if (engine != null) {
            engine!!.leaveChannel()
            engine!!.stopScreenCapture()
            engine!!.stopPreview()
        }
        engine = null
        super.onDestroy()
        RtcEngine.destroy()
    }


    override fun onCheckedChanged(compoundButton: CompoundButton, checked: Boolean) {
        if (compoundButton === screenPreview) {
            if (!joined) {
                return
            }
            if (checked) {
                startScreenSharePreview()
            } else {
                stopScreenSharePreview()
            }
        } else if (compoundButton === screenAudio) {
            if (!joined) {
                return
            }
            screenCaptureParameters.captureAudio = checked
            engine!!.updateScreenCaptureParameters(screenCaptureParameters)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_join) {
            if (!joined) {
//                CommonUtil.hideInputBoard(this, et_channel)
                // call when join button hit
                val channelId = et_channel!!.text.toString()
                // Check permission
//                if (AndPermission.hasPermissions(this,
//                        Permission.Group.STORAGE,
//                        Permission.Group.MICROPHONE,
//                        Permission.Group.CAMERA)
//                ) {
                    joinChannel(channelId)
                    return
                }
                // Request permission
//                AndPermission.with(this).runtime().permission(
//                    Permission.Group.STORAGE,
//                    Permission.Group.MICROPHONE,
//                    Permission.Group.CAMERA
//                ).onGranted { permissions ->
//                    // Permissions Granted
//                    joinChannel(channelId)
//                }.start()
            } else {
                leaveChannel()
            }
//        }
    }

    private fun startScreenSharePreview() {
        // Check if the context is valid
        val context: Context = this ?: return

        // Create render view by RtcEngine
        val surfaceView = SurfaceView(context)
        if (fl_local!!.childCount > 0) {
            fl_local!!.removeAllViews()
        }
        // Add to the local container
        fl_local!!.addView(surfaceView,
            FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT))
        // Setup local video to render your local camera preview
        engine!!.setupLocalVideo(VideoCanvas(surfaceView, Constants.RENDER_MODE_FIT,
            Constants.VIDEO_MIRROR_MODE_DISABLED,
            Constants.VIDEO_SOURCE_SCREEN_PRIMARY,
            0))
        engine!!.startPreview(VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY)
    }

    private fun stopScreenSharePreview() {
        fl_local!!.removeAllViews()
        engine!!.setupLocalVideo(VideoCanvas(null, Constants.RENDER_MODE_FIT,
            Constants.VIDEO_MIRROR_MODE_DISABLED,
            Constants.VIDEO_SOURCE_SCREEN_PRIMARY,
            0))
        engine!!.stopPreview(VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY)
    }


    private fun joinChannel(channelId: String) {
        engine!!.setParameters("{\"che.video.mobile_1080p\":true}")
        engine!!.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        */
/**Enable video module *//*

        engine!!.enableVideo()
        // Setup video encoding configs
        engine!!.setVideoEncoderConfiguration(VideoEncoderConfiguration(
            VideoEncoderConfiguration.VD_640x360,
            FRAME_RATE_FPS_15,
            VideoEncoderConfiguration.STANDARD_BITRATE,
            ORIENTATION_MODE_ADAPTIVE
        ))
        */
/**Set up to play remote sound with receiver *//*

        engine!!.setDefaultAudioRoutetoSpeakerphone(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.startForegroundService(fgServiceIntent)
        }
        val metrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics)
        screenCaptureParameters.captureVideo = true
        screenCaptureParameters.videoCaptureParameters.width = 720
        screenCaptureParameters.videoCaptureParameters.height =
            (720 * 1.0f / metrics.widthPixels * metrics.heightPixels).toInt()
        screenCaptureParameters.videoCaptureParameters.framerate = DEFAULT_SHARE_FRAME_RATE
        screenCaptureParameters.captureAudio = screenAudio!!.isChecked
        screenCaptureParameters.audioCaptureParameters.captureSignalVolume =
            screenAudioVolume!!.progress
        engine!!.startScreenCapture(screenCaptureParameters)
        if (screenPreview?.isChecked() == true) {
            startScreenSharePreview()
        }
        */
/**Please configure accessToken in the string_config file.
         * A temporary token generated in Console. A temporary token is valid for 24 hours. For details, see
         * https://docs.agora.io/en/Agora%20Platform/token?platform=All%20Platforms#get-a-temporary-token
         * A token generated at the server. This applies to scenarios with high-security requirements. For details, see
         * https://docs.agora.io/en/cloud-recording/token_server_java?platform=Java *//*

        // Fill the temp token generated on Agora Console.
        val token =
            "007eJxTYOCyuvfAr37iSe+UXYGSqpvunOzy3zN1vq1IcF/rHMc97IIKDKbmBkmJxgbGFkaGKSapppZJSakmqUapiQapRiYmiSmWzBNKkhsCGRmmPd/LwsgAgSA+G0N6Yl5qcQYDAwBKRB+Y";

        token.let {
            */
/** Allows a user to join a channel.
             * if you do not specify the uid, we will generate the uid for you *//*

            // set options
            val options = ChannelMediaOptions()
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            options.autoSubscribeVideo = true
            options.autoSubscribeAudio = true
            options.publishCameraTrack = false
            options.publishMicrophoneTrack = false
            options.publishScreenCaptureVideo = true
            options.publishScreenCaptureAudio = true
            val res = engine!!.joinChannel(token, channelId, 0, options)
            if (res != 0) {
                // Usually happens with invalid parameters
                // Error code description can be found at:
                // en: https://docs.agora.io/en/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html
                // cn: https://docs.agora.io/cn/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html
                showAlert(RtcEngine.getErrorDescription(Math.abs(res)))
            }
            // Prevent repeated entry
            join!!.isEnabled = false
        }
    }

    fun showAlert(error:String) {
        Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
    }

    */
/**
     * IRtcEngineEventHandler is an abstract class providing default implementation.
     * The SDK uses this class to report to the app on SDK runtime events.
     *//*

    private val iRtcEngineEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        */
/**Reports a warning during SDK runtime.
         * Warning code: https://docs.agora.io/en/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_warn_code.html *//*

        override fun onWarning(warn: Int) {
            Log.w(TAG,
                String.format("onWarning code %d message %s",
                    warn,
                    RtcEngine.getErrorDescription(warn)))
        }

        */
/**Reports an error during SDK runtime.
         * Error code: https://docs.agora.io/en/Voice/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_i_rtc_engine_event_handler_1_1_error_code.html *//*

        override fun onError(err: Int) {
            Log.e(TAG,
                String.format("onError code %d message %s",
                    err,
                    RtcEngine.getErrorDescription(err)))
            showAlert(String.format("onError code %d message %s",
                err,
                RtcEngine.getErrorDescription(err)))
        }

        */
/**Occurs when the local user joins a specified channel.
         * The channel name assignment is based on channelName specified in the joinChannel method.
         * If the uid is not specified when joinChannel is called, the server automatically assigns a uid.
         * @param channel Channel name
         * @param uid User ID
         * @param elapsed Time elapsed (ms) from the user calling joinChannel until this callback is triggered
         *//*

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
//            Log.i(TAG, String.format("onJoinChannelSuccess channel %s uid %d", channel, uid))
            showAlert(String.format("onJoinChannelSuccess channel %s uid %d", channel, uid))
            myUid = uid
            joined = true
                join!!.isEnabled = true
                join!!.text = "leave"
        }

        override fun onLocalVideoStateChanged(source: VideoSourceType, state: Int, error: Int) {
            super.onLocalVideoStateChanged(source, state, error)
//            Log.i(TAG,
//                "onLocalVideoStateChanged source=$source, state=$state, error=$error")
            if (source == VideoSourceType.VIDEO_SOURCE_SCREEN_PRIMARY) {
                if (state == Constants.LOCAL_VIDEO_STREAM_STATE_ENCODING) {
                    if (error == Constants.ERR_OK) {
//                        showLongToast("Screen sharing start successfully.")
                    }
                } else if (state == Constants.LOCAL_AUDIO_STREAM_STATE_FAILED) {
                    if (error == Constants.ERR_SCREEN_CAPTURE_SYSTEM_NOT_SUPPORTED) {
//                        showLongToast("Screen sharing has been cancelled")
                    } else {
//                        showLongToast("Screen sharing start failed for error $error")
                    }
                    leaveChannel()
                }
            }
        }

        */
/**Since v2.9.0.
         * Occurs when the remote video state changes.
         * PS: This callback does not work properly when the number of users (in the Communication
         * profile) or broadcasters (in the Live-broadcast profile) in the channel exceeds 17.
         * @param uid ID of the remote user whose video state changes.
         * @param state State of the remote video:
         * REMOTE_VIDEO_STATE_STOPPED(0): The remote video is in the default state, probably due
         * to REMOTE_VIDEO_STATE_REASON_LOCAL_MUTED(3), REMOTE_VIDEO_STATE_REASON_REMOTE_MUTED(5),
         * or REMOTE_VIDEO_STATE_REASON_REMOTE_OFFLINE(7).
         * REMOTE_VIDEO_STATE_STARTING(1): The first remote video packet is received.
         * REMOTE_VIDEO_STATE_DECODING(2): The remote video stream is decoded and plays normally,
         * probably due to REMOTE_VIDEO_STATE_REASON_NETWORK_RECOVERY (2),
         * REMOTE_VIDEO_STATE_REASON_LOCAL_UNMUTED(4), REMOTE_VIDEO_STATE_REASON_REMOTE_UNMUTED(6),
         * or REMOTE_VIDEO_STATE_REASON_AUDIO_FALLBACK_RECOVERY(9).
         * REMOTE_VIDEO_STATE_FROZEN(3): The remote video is frozen, probably due to
         * REMOTE_VIDEO_STATE_REASON_NETWORK_CONGESTION(1) or REMOTE_VIDEO_STATE_REASON_AUDIO_FALLBACK(8).
         * REMOTE_VIDEO_STATE_FAILED(4): The remote video fails to start, probably due to
         * REMOTE_VIDEO_STATE_REASON_INTERNAL(0).
         * @param reason The reason of the remote video state change:
         * REMOTE_VIDEO_STATE_REASON_INTERNAL(0): Internal reasons.
         * REMOTE_VIDEO_STATE_REASON_NETWORK_CONGESTION(1): Network congestion.
         * REMOTE_VIDEO_STATE_REASON_NETWORK_RECOVERY(2): Network recovery.
         * REMOTE_VIDEO_STATE_REASON_LOCAL_MUTED(3): The local user stops receiving the remote
         * video stream or disables the video module.
         * REMOTE_VIDEO_STATE_REASON_LOCAL_UNMUTED(4): The local user resumes receiving the remote
         * video stream or enables the video module.
         * REMOTE_VIDEO_STATE_REASON_REMOTE_MUTED(5): The remote user stops sending the video
         * stream or disables the video module.
         * REMOTE_VIDEO_STATE_REASON_REMOTE_UNMUTED(6): The remote user resumes sending the video
         * stream or enables the video module.
         * REMOTE_VIDEO_STATE_REASON_REMOTE_OFFLINE(7): The remote user leaves the channel.
         * REMOTE_VIDEO_STATE_REASON_AUDIO_FALLBACK(8): The remote media stream falls back to the
         * audio-only stream due to poor network conditions.
         * REMOTE_VIDEO_STATE_REASON_AUDIO_FALLBACK_RECOVERY(9): The remote media stream switches
         * back to the video stream after the network conditions improve.
         * @param elapsed Time elapsed (ms) from the local user calling the joinChannel method until
         * the SDK triggers this callback.
         *//*

        override fun onRemoteVideoStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
            super.onRemoteVideoStateChanged(uid, state, reason, elapsed)
//            Log.i(TAG, "onRemoteVideoStateChanged:uid->$uid, state->$state")
        }

        override fun onRemoteVideoStats(stats: RemoteVideoStats) {
            super.onRemoteVideoStats(stats)
//            Log.d(TAG, "onRemoteVideoStats: width:" + stats.width + " x height:" + stats.height)
        }

        */
/**Occurs when a remote user (Communication)/host (Live Broadcast) joins the channel.
         * @param uid ID of the user whose audio state changes.
         * @param elapsed Time delay (ms) from the local user calling joinChannel/setClientRole
         * until this callback is triggered.
         *//*

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
//            Log.i(TAG, "onUserJoined->$uid")
//            showLongToast(String.format("user %d joined!", uid))
            if (remoteUid > 0) {
                return
            }
            remoteUid = uid
                val renderView = SurfaceView(this@AgoraMainActivity)
                engine!!.setupRemoteVideo(VideoCanvas(renderView,
                    Constants.RENDER_MODE_FIT,
                    uid))
                fl_remote?.removeAllViews()
                fl_remote?.addView(renderView)
        }

        */
/**Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         * @param uid ID of the user whose audio state changes.
         * @param reason Reason why the user goes offline:
         * USER_OFFLINE_QUIT(0): The user left the current channel.
         * USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data
         * packet was received within a certain period of time. If a user quits the
         * call and the message is not passed to the SDK (due to an unreliable channel),
         * the SDK assumes the user dropped offline.
         * USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from
         * the host to the audience.
         *//*

        override fun onUserOffline(uid: Int, reason: Int) {
//            Log.i(TAG, String.format("user %d offline! reason:%d", uid, reason))
//            showLongToast(String.format("user %d offline! reason:%d", uid, reason))
            if (remoteUid == uid) {
                remoteUid = -1
                    fl_remote?.removeAllViews()
                    engine!!.setupRemoteVideo(VideoCanvas(null,
                        Constants.RENDER_MODE_FIT,
                        uid))
            }
        }
    }

    private fun leaveChannel() {
        joined = false
        join!!.text = "Join"
        fl_local!!.removeAllViews()
        fl_remote!!.removeAllViews()
        myUid = -1
        remoteUid = myUid
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.stopService(fgServiceIntent)
        }
        engine!!.leaveChannel()
        engine!!.stopScreenCapture()
        engine!!.stopPreview()
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (seekBar === screenAudioVolume) {
            if (!joined) {
                return
            }
            screenCaptureParameters.audioCaptureParameters.captureSignalVolume = progress
            engine!!.updateScreenCaptureParameters(screenCaptureParameters)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    class MediaProjectFgService : Service() {
        @Nullable
        override fun onBind(intent: Intent?): IBinder? {
            return null
        }

        override fun onCreate() {
            super.onCreate()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                createNotificationChannel()
            }
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            return START_NOT_STICKY
        }

        override fun onDestroy() {
            super.onDestroy()
//            ServiceCompat.stopForeground(this,0)
        }

        */
/*@RequiresApi(api = Build.VERSION_CODES.O)
        private fun createNotificationChannel() {
            val name: CharSequence = getString(R.string.app_name)
            val description = "Notice that we are trying to capture the screen!!"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channelId = "agora_channel_mediaproject"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            val notifyId = 1
            // Create a notification and set the notification channel.
            val notification: Notification = Notification.Builder(this, channelId)
                .setContentText(name.toString() + "正在录制屏幕内容...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setChannelId(channelId)
                .setWhen(System.currentTimeMillis())
                .build()
            DownloadService.startForeground(notifyId, notification)
        }*//*

    }
}
*/
