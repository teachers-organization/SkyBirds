package com.example.skybird.Modelo.BBDD

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SkybirdDAO {

    //Usuarios
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: Users)

    @Delete
    suspend fun deleteUser(user: Users)

    @Update
    suspend fun updateUser(user: Users)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<Users>>

    @Query("SELECT * FROM users WHERE nombreCompleto = :nombre")
    suspend fun getUsersByName(nombre: String): List<Users>

    @Query("SELECT * FROM users WHERE id = :query")
    fun getUserById(query: Int): Flow<Users>

    @Query("SELECT * FROM users WHERE email = :email AND psswd = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): Users?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): Users?

    //Foro
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestion(question: Questions)

    @Query("SELECT * FROM pregunta_usuario")
    fun getAllQuestions(): Flow<List<Questions>>

    @Delete
    suspend fun deleteQuestion(question: Questions)

    @Query("SELECT * FROM respuesta_usuario WHERE questionId = :query")
    fun getAnswerByIdQuestion(query: Int): Flow<List<Answers>>

    @Delete
    suspend fun deleteAnswer(answers: Answers)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnswer(answers: Answers)

    //Anillamientos
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnillamiento(anillamiento: Anillamiento)

    @Query("SELECT * FROM anillamientos")
    fun getAllAnillamientos(): Flow<List<Anillamiento>>

    @Delete
    suspend fun deleteNillamiento(anillamiento: Anillamiento)

    @Query("SELECT * FROM anillamientos WHERE codigoAnillamiento = :query")
    fun getAnillamientoByCodigo(query: String): Flow<Anillamiento>

    @Query("SELECT * FROM especies WHERE nombre = :query LIMIT 1")
    fun getEspecieByName(query: String): Flow<Especie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEspecie(especie: Especie)

}