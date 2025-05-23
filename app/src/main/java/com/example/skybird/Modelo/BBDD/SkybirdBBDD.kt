package com.example.skybird.Modelo.BBDD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class, Questions::class, Answers::class, Anillamiento::class, Especie::class], version = 4, exportSchema = false)
abstract class SkybirdBBDD : RoomDatabase(){
    abstract fun SkybirdDAO(): SkybirdDAO
    companion object{
        @Volatile //El valor de una variable volatile nunca se almacena en caché
        private var Instance: SkybirdBBDD? = null //The Instance variable keeps a reference to the database, when one has been created
        //los cambios realizados por un hilo en Instance son inmediatamente visibles para todos los demás hilos.

        //Encerrar el código para obtener la base de datos dentro de un bloque synchronized asegura que solo
        // un hilo de ejecución pueda ingresar a este bloque de código a la vez, garantizando que la base de datos solo se inicialice una vez
        fun getDatabase(context: Context): SkybirdBBDD {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, SkybirdBBDD::class.java, "SkybirdBBDD").fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
