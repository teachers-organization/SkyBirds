package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SesionViewModel : ViewModel() {

    //Almacenar usuario al iniciar sesión
    var usuarioActual = mutableStateOf<Users?>(null)

    //Comprobar si el usuario existe en la base de datos
    //Si existe iniciamos sesión y almacenamos el usuario
    fun iniciarSesion(
        skybirdDAO: SkybirdDAO,
        email: String,
        psswd: String,
        onResultado: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userExists = skybirdDAO.getUserByEmailAndPassword(email, psswd)
            if (userExists == null) {
                onResultado(false)
            } else {
                usuarioActual.value = userExists
                onResultado(true)
            }
        }
    }

    fun crearAdmin(skybirdDAO: SkybirdDAO) {
        viewModelScope.launch {
            val userExists = skybirdDAO.getUserByEmailAndPassword("admin@admin.com", "admin")
            if (userExists == null) {
                skybirdDAO.insertUser(
                    Users(
                        id = 0,
                        nombreCompleto = "admin",
                        admin = true,
                        nick = "admin",
                        email = "admin@admin.com",
                        psswd = "admin"
                    )
                )
            }
        }
    }

    //Función para cambiar de contraseña
    fun cambiarContrasenya(
        skybirdDAO: SkybirdDAO,
        contrasenyaNueva: String,
        contrasenyaAntigua: String,
        onResultado: (Boolean) -> Unit
    ) {

        if (contrasenyaAntigua == usuarioActual.value?.psswd) {

            //Creamos una copia del usuario actual, cambiamos el valor de la contraseña
            //y lo actualizamos en la base de datos

            usuarioActual.value?.let { usuario ->
                usuarioActual.value = usuario.copy(psswd = contrasenyaNueva)
            }

            val userActualizar: Users = usuarioActual.value!!
            viewModelScope.launch {
                skybirdDAO.updateUser(userActualizar)
            }

            onResultado(true)

        } else {
            onResultado(false)
        }
    }

    //Función para cambiar de nombre
    fun cambiarNombre(
        skybirdDAO: SkybirdDAO,
        nombreNuevo: String,
        onResultado: (Boolean) -> Unit
    ) {

        try {
            //Creamos una copia del usuario actual, cambiamos el valor del nombre
            //y lo actualizamos en la base de datos

            usuarioActual.value?.let { usuario ->
                usuarioActual.value = usuario.copy(nombreCompleto = nombreNuevo)
            }

            val userActualizar: Users = usuarioActual.value!!
            viewModelScope.launch {
                skybirdDAO.updateUser(userActualizar)
            }

            onResultado(true)

        } catch (e: Exception) {
            onResultado(false)
        }
    }

    //Función para cambiar de nick
    fun cambiarNick(
        skybirdDAO: SkybirdDAO,
        nuevoNick: String,
        onResultado: (Boolean) -> Unit
    ) {

        try {
            //Creamos una copia del usuario actual, cambiamos el valor del nick
            //y lo actualizamos en la base de datos

            usuarioActual.value?.let { usuario ->
                usuarioActual.value = usuario.copy(nick = nuevoNick)
            }

            val userActualizar: Users = usuarioActual.value!!
            viewModelScope.launch {
                skybirdDAO.updateUser(userActualizar)
            }

            onResultado(true)

        } catch (e: Exception) {
            onResultado(false)
        }
    }

    //Función para borrar usuario
    fun borrarUsuario(skybirdDAO: SkybirdDAO, users: Users) {
        try {
            viewModelScope.launch {
                skybirdDAO.deleteUser(users)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Función para cerrar sesión
    fun cerrarSesión() {
        try {
            usuarioActual.value = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val _listaUsuarios = MutableStateFlow<List<Users>>(emptyList())
    val listaUsuarios: StateFlow<List<Users>> = _listaUsuarios

    //Función para obtener todos los usuarios
    fun obtenerUsuarios(skybirdDAO: SkybirdDAO) {
        viewModelScope.launch {
            skybirdDAO.getAllUsers().collect { usuarios ->
                _listaUsuarios.value = usuarios
            }
        }
    }

    //Función para filtrar por nombre de usuario
    fun filtrarNombre(lista: List<Users>, texto: String): List<Users> {
        val listaFiltrada = lista.filter { user ->
            val regex = Regex(".*${Regex.escape(texto)}.*", RegexOption.IGNORE_CASE)
            regex.containsMatchIn(user.nick)
        }
        return listaFiltrada
    }

    fun validarContrasenya(contrasenya: String): Boolean {
        //Al menos una mayúscula y al menos un carácter especial
        val regex = Regex("^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{1,}\$")
        return regex.matches(contrasenya)
    }



}