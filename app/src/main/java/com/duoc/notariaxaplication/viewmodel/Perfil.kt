package com.duoc.notariaxaplication.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.duoc.notariaxaplication.model.DatosPersonalesModel
import com.duoc.notariaxaplication.model.RegisterModel
import com.duoc.notariaxaplication.model.UsuarioModel

class Perfil {
        // 1. Declaración de variables observables del Perfil
        var fotoPerfil by mutableStateOf<Bitmap?>(null)

        var nombre by mutableStateOf("")
        var ap_paterno by mutableStateOf("")
        var ap_materno by mutableStateOf("")
        var run by mutableStateOf("")
        var direccion by mutableStateOf("")
        var correo by mutableStateOf("")

        // 2. Función para recibir y traspasar los datos
        fun traspasarDatosDelRegistro(
            usuarioModel: UsuarioModel,
            register: RegisterModel
        ) {
            nombre = usuarioModel.nombre
            ap_paterno = usuarioModel.ap_paterno
            ap_materno = usuarioModel.ap_materno
            run = usuarioModel.run
            direccion = usuarioModel.direccion
            correo = usuarioModel.correo
        }
    }