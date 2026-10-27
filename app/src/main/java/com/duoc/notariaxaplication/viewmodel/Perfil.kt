package com.duoc.notariaxaplication.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.duoc.notariaxaplication.model.DatosPersonalesModel
import com.duoc.notariaxaplication.model.RegisterModel

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
            datosPersonales: DatosPersonalesModel,
            register: RegisterModel
        ) {
            nombre = datosPersonales.nombre
            ap_paterno = datosPersonales.ap_paterno
            ap_materno = datosPersonales.ap_materno
            run = datosPersonales.run
            direccion = datosPersonales.direccion
            correo = register.correo
        }
    }