package com.duoc.notariaxaplication.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LogginModel {

    var run by mutableStateOf("")

    var contraseña by mutableStateOf("")
}