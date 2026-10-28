package com.duoc.notariaxaplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.notariaxaplication.repository.LogginRepository
import com.duoc.notariaxaplication.model.LogginModel
import com.duoc.notariaxaplication.model.MensajeError

class LogginViewModel : ViewModel() {

    private val repository = LogginRepository()

    var loggin : LogginModel by mutableStateOf( repository.getLoggin())

    var mensajeError: MensajeError by mutableStateOf(repository.getMsError())

    fun verificarLoggin(): Boolean {
        return verificarRun() &&
                verificarContrasenia()
    }

    fun verificarRun(): Boolean {
        if(!repository.validacionRun()){
            mensajeError.run = "el formato de run no es válido"
            return false
        } else {
            mensajeError.run = ""
            return true
        }
    }

    fun verificarContrasenia(): Boolean {
        if (!repository.validacionContrasenia()) {
            mensajeError.contrasenia = "La contraseña no puede estar vacía"
            return false
        } else {
            mensajeError.contrasenia = ""
            return true
        }
    }
}