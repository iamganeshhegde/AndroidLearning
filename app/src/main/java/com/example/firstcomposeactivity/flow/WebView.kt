package com.example.firstcomposeactivity.flow

import android.util.Base64
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.common.base.Charsets


@Composable
fun FullWebViewUI(
) {

    val data = "<!DOCTYPE html> <head> <link href=\"https://fonts.googleapis.com/css?family=Nunito\" rel=\"stylesheet\" /></head> <html lang=\"en\"><body style=\"margin: 0\"> <div style=\" background-color: #000; color: #fff; height: 100%; width: 100%; padding: 16px; box-sizing: border-box; font-family: nunito; \" > <div style=\"padding-bottom: 14px\"> <h3 style=\"font-size: 16px; font-weight: 700; margin: 0px 0px 8px\"> What are Live Goals? </h3><p style=\"font-size: 14px; font-weight: 400; margin: 0\">  Live Goals is a fun feature that allows hosts to set a target for receiving a particular gift and achieve it with the support of their audience. Supporters will have the opportunity to earn a spot on the leaderboard by sending that specific gift during the livestream. </p> </div> <div style=\"padding-bottom: 8px; font-size: 14px; font-weight: 400\"> <h3 style=\"font-size: 16px; font-weight: 700; margin: 0px 0px 8px\"> How to participate? </h3> <div style=\"margin: 0\"> <p style=\"margin-bottom: 6px\"> 1. Look out for <img src=\"https://cdn4.sharechat.com/10d179fa_1694001864993.png\" alt=\"the Live Goals icon\" width=\"60\" height=\"17\"> to see the Live Goal set by the host and send that gift on Live. </p> <p style=\"margin-bottom: 6px\"> 2. Top supporters will feature on the leaderboard and get shoutouts from the host. </p> </div> </div> <div style=\"padding-bottom: 8px; font-size: 14px; font-weight: 400\"> <h3 style=\"font-size: 16px; font-weight: 700; margin: 0px 0px 8px\">Things to remember  </h3> <div style=\"margin: 0\"> <p style=\"margin-bottom: 6px\"> Goals are independent of Battles and can be contributed to at any time. </p> </div> </div> </div> </body></html>"
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            ,
        content = {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadData(
                        Base64.encodeToString(data.toString().toByteArray(Charsets.UTF_8), Base64.NO_PADDING),
                        "text/html",
                        "base64")
                }
            })
        }
    )
}


