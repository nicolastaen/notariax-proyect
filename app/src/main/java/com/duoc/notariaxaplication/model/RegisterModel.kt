package com.duoc.notariaxaplication.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RegisterModel {
    var correo by mutableStateOf("")

    var contrasenia by mutableStateOf("")

    var con_contrasenia by mutableStateOf(false)
}