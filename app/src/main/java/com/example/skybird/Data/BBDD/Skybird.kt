package com.example.skybird.Data.BBDD

import androidx.room.Entity
import androidx.room.ForeignKey
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

@Entity(
    tableName = "pregunta_usuario",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val contenido: String,
    val fechaCreacion: Long,
    val userId: Int
)

@Entity(
    tableName = "respuesta_usuario",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Questions::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Answers(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val contenido: String,
    val fechaCreacion: Long,
    val userId: Int,
    val questionId: Int
)








