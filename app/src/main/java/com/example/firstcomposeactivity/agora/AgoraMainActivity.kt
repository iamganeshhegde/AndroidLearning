package com.example.firstcomposeactivity.agora

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process.myUid
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firstcomposeactivity.R
import io.agora.rtc.Constants
import io.agora.rtc.Constants.RELAY_STATE_CONNECTING
import io.agora.rtc.Constants.RELAY_STATE_FAILURE
import io.agora.rtc.Constants.RELAY_STATE_IDLE
import io.agora.rtc.Constants.RELAY_STATE_RUNNING
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.models.ChannelMediaOptions
import io.agora.rtc.models.ClientRoleOptions
import io.agora.rtc.video.ChannelMediaInfo
import io.agora.rtc.video.ChannelMediaRelayConfiguration
import io.agora.rtc.video.VideoCanvas


class AgoraMainActivity : AppCompatActivity() {


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
        "006570ba303821d4e59bbe4e2ea0e244ad9IAAjO1oZ0Zae/Nz109JuZ+kLJpetQPqT2uz6+zSOoSRrwRiK/9IAAAAAEABiLYCEcr70YgEAAQByvvRi";

    val token2 = "006570ba303821d4e59bbe4e2ea0e244ad9IAACbqGc1dC8s8D1towcKwULdI8BzFSA5YjhGjNRa99ZeXEqSRIAAAAAEABiLYCEur70YgEAAQC6vvRi"

    lateinit var channel1:EditText
    lateinit var channel2:EditText

    lateinit var rtcEngine: RtcEngine

    val rtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)

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
            Log.d("Ganesh", " onJoinChannelSuccess chammel- ${channel}, uid {$uid}, elapsed - $elapsed")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            Log.d("Ganesh", " onUserOffline - uid {$uid}")

        }

        override fun onChannelMediaRelayStateChanged(state: Int, code: Int) {
            super.onChannelMediaRelayStateChanged(state, code)

            when (state) {
                RELAY_STATE_CONNECTING -> {

                    Log.d("Ganesh", " onChannelMediaRelayStateChanged - connecting state {$state}, code - $code")

                }

                RELAY_STATE_RUNNING -> {
                    Log.d("Ganesh", " onChannelMediaRelayStateChanged - RELAY_STATE_RUNNING state {$state}, code - $code")

                }
                RELAY_STATE_FAILURE -> {
                    Log.d("Ganesh", " onChannelMediaRelayStateChanged - fail state {$state}, code - $code")

                }
                RELAY_STATE_IDLE -> {
                    Log.d("Ganesh", " onChannelMediaRelayStateChanged - RELAY_STATE_IDLE state {$state}, code - $code")
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


        setContentView(R.layout.agora_main_activity)


        channel1 = findViewById<EditText>(R.id.channel1)
        channel2 = findViewById<EditText>(R.id.channel2)



        val roleChange = findViewById<Button>(R.id.role)

        val joinChannel = findViewById<Button>(R.id.joinChannel)
        val joinAnotherChannel = findViewById<Button>(R.id.joinAnotherChannel)
        val relay = findViewById<Button>(R.id.relay)


        roleChange.setOnClickListener {
           if(role == Constants.CLIENT_ROLE_AUDIENCE) {
                role = Constants.CLIENT_ROLE_BROADCASTER
               joinChannel.text = "Join as host"
               joinAnotherChannel.text =  "Join as host 2"
            } else {
                role = Constants.CLIENT_ROLE_AUDIENCE
               joinChannel.text = "Join as audience"
               joinAnotherChannel.text =  "Join as audiece 2"
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


        /*setContent {
            FirstComposeActivityTheme {

            }
        }*/
    }

    private fun joinChannel(joinChannel: Int) {
        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
            checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)) {
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
            setClientRole(/*Constants.CLIENT_ROLE_BROADCASTER*/ role , clientRoleOptions)
            enableVideo()

        }


        val container = findViewById<FrameLayout>(R.id.local_video_view_container)

        val surfaceView = RtcEngine.CreateRendererView(baseContext)

        container.addView(surfaceView)


        rtcEngine.setupLocalVideo(
            VideoCanvas(
                surfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                if(joinChannel == 1) {0} else {0}
            )
        )

        val channel1 = channel1.text
        val channel2 = channel2.text

        val option = ChannelMediaOptions()
        option.autoSubscribeAudio = true
        option.autoSubscribeVideo = true

        if(joinChannel == 1) {
            rtcEngine.joinChannel(token, /*this.channelName*/channel1.toString(), "", 0, option)
        } else {

            rtcEngine.joinChannel(token2, /*this.channelName*/channel2.toString(), "", 0, option)

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
        /*et_channel_ex.setEnabled(false)
        join_ex.setEnabled(false)*/
    }


    override fun onDestroy() {
        super.onDestroy()
        rtcEngine.leaveChannel()
        rtcEngine.removeHandler(rtcEventHandler)

        /*val channelMediaRelayConfiguration = ChannelMediaRelayConfiguration()
        rtcEngine.startChannelMediaRelay(ChannelMediaRelayConfiguration())*/
    }
}