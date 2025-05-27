package com.example.skybird.Modelo.BBDD

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

@Entity(
    tableName = "anillamientos",
    foreignKeys = [
        ForeignKey(
            entity = Users::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Especie::class,
            parentColumns = ["id"],
            childColumns = ["idEspecie"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Anillamiento(
    @PrimaryKey
    val codigoAnillamiento: String,
    val idEspecie: Int,
    val fecha: String,
    val lugar: String,
    val edad: String? = null,
    val sexo: String? = null,
    val peso: String? = null,
    val ala: String? = null,
    val observaciones: String? = null,
    val userId: Int
)

@Entity(tableName = "especies")
data class Especie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String
)

@Entity(
    tableName = "avistamientos",
    foreignKeys = [
        ForeignKey(
            entity = Anillamiento::class,
            parentColumns = ["codigoAnillamiento"],
            childColumns = ["codigoAnillamiento"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Avistamiento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigoAnillamiento: String,
    val fecha: String,
    val lugar: String,
    val edad: String? = null,
    val sexo: String? = null,
    val peso: String? = null,
    val ala: String? = null,
    val observaciones: String? = null,
    val userId: Int
)


