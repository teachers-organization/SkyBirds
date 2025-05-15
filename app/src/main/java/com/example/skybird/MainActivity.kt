package com.example.skybird

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.skybird.Controlador.Navegador
import com.example.skybird.Modelo.BBDD.SkybirdBBDD
import com.example.skybird.ui.theme.SkyBirdTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //instancia de SkybirdDao para ejecutar las consultas
        val SkybirdBBDD = Room.databaseBuilder(
            applicationContext,
            SkybirdBBDD::class.java, "SkybirdBBDD"
        ).fallbackToDestructiveMigration()
            .build()
        val SkybirdDAO = SkybirdBBDD.SkybirdDAO()
        enableEdgeToEdge()
        setContent {
            SkyBirdTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navegador(SkybirdDAO, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkyBirdTheme {
        Greeting("Android")
    }
}