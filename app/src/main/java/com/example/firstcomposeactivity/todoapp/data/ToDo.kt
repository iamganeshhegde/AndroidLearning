package com.example.firstcomposeactivity.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    val title:String,
    val description:String?,
    val isDone:Boolean,
    @PrimaryKey val id:Int? = null
)
