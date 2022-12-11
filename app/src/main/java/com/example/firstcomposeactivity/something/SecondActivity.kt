package com.example.firstcomposeactivity.something

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.firstcomposeactivity.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity:AppCompatActivity() {

    val secondVM by viewModels<SecondViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.second_activity_layout)



        secondVM.doSomething()


    }
}