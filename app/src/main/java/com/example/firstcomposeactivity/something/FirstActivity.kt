package com.example.firstcomposeactivity.something

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.firstcomposeactivity.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {

    val firstViewModel by viewModels<FirstViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContentView(R.layout.first_activity)


        findViewById<Button>(R.id.second_activity_click).setOnClickListener {

            startActivity(Intent(this, SecondActivity::class.java))
        }

        firstViewModel.callingfromActivity()



    }

}