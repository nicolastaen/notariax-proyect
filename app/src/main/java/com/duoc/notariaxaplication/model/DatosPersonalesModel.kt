package com.duoc.notariaxaplication.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DatosPersonalesModel {

    var nombre by mutableStateOf("")

    var ap_paterno by mutableStateOf("")

    var ap_materno by mutableStateOf("")

    var run by mutableStateOf("")

    var direccion by mutableStateOf("")
}