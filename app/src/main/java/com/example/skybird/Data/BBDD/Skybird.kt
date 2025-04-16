package com.example.skybird.Data.BBDD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreCompleto: String,
    val admin: Boolean,
    val nick: String,
    val email: String,
    val psswd: String,
)