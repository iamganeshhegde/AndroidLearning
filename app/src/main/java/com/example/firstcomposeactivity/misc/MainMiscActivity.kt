package com.example.firstcomposeactivity.misc

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firstcomposeactivity.misc.navigationcompose.Navigation
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage


class MainMiscActivity:ComponentActivity() {

    var client: MqttAndroidClient? = null
    var subText = mutableStateOf("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {


            Navigation()


            /*Column {
                Button(onClick = {published()  }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                    Text(text = "Publish")
                }
                
                Text(text = subText.value, modifier = Modifier.padding(16.dp))

                Button(onClick = { conn() }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                    Text(text = "COnnect")
                }

                Button(onClick = { disconn() }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                    Text(text = "disconnect")
                }

            }*/

        }


        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(
            this.applicationContext,
            "tcp://broker.mqttdashboard.com:1883",
            clientId
        )

        Toast.makeText(this@MainMiscActivity, "clientId!! ${clientId}", Toast.LENGTH_LONG).show()
        Toast.makeText(this@MainMiscActivity, "client!! ${client}", Toast.LENGTH_LONG).show()


        //client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.43.41:1883",clientId);

        //client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.43.41:1883",clientId);

        try {
            val token: IMqttToken = client!!.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Toast.makeText(this@MainMiscActivity, "connected!!", Toast.LENGTH_LONG).show()
                    setSubscription()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Toast.makeText(this@MainMiscActivity, "connection failed!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
            Toast.makeText(this@MainMiscActivity, "e - ${e.localizedMessage}", Toast.LENGTH_LONG)
                .show()
        }

        client!!.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable) {}

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                subText.value = message.payload.toString()
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })

    }

    fun published() {
        val topic = "event"
        val message = "the payload"
        try {
            client!!.publish(topic, message.toByteArray(), 0, false)
            Toast.makeText(this, "Published Message", Toast.LENGTH_SHORT).show()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    private fun setSubscription() {
        try {
            client?.subscribe("event", 0)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun conn() {
        try {
            val token: IMqttToken = client!!.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Toast.makeText(this@MainMiscActivity, "connected!!", Toast.LENGTH_LONG).show()
                    setSubscription()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Toast.makeText(this@MainMiscActivity, "connection failed!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }


    fun disconn()
    {

        try {
            val token: IMqttToken = client!!.disconnect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Toast.makeText(this@MainMiscActivity, "Disconnected!!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Toast.makeText(this@MainMiscActivity, "Could not diconnect!!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}