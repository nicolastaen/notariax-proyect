package com.duoc.notariaxaplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariaxaplication.repository.LogginRepository
import com.duoc.notariaxaplication.repository.SesionManager
import com.duoc.notariaxaplication.repository.UsuarioRepository
import com.duoc.notariaxaplication.model.LogginModel
import com.duoc.notariaxaplication.model.MensajeError
import com.duoc.notariaxaplication.model.UsuarioModel
import kotlinx.coroutines.launch

class LogginViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val sesionManager: SesionManager
) : ViewModel() {

    private val repository = LogginRepository()

    var loggin : LogginModel by mutableStateOf( repository.getLoggin())

    var mensajeError: MensajeError by mutableStateOf(repository.getMsError())

    var cargando by mutableStateOf(false)
    var errorLogin by mutableStateOf("")

    var usuarioActual by mutableStateOf<UsuarioModel?>(null)
        private set

    var verificandoSesion by mutableStateOf(true)
        private set

    init {
        restaurarSesion()
    }

    private fun restaurarSesion() {
        val run = sesionManager.obtenerRun()
        if (run == null) {
            verificandoSesion = false
            return
        }
        viewModelScope.launch {
            try {
                usuarioActual = usuarioRepository.obtenerPorRun(run)
                // Si el usuario ya no existe en la BD, se descarta la sesión guardada.
                if (usuarioActual == null) sesionManager.cerrar()
            } catch (e: Exception) {
                usuarioActual = null
            } finally {
                verificandoSesion = false
            }
        }
    }

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

    fun iniciarSesion(onExito: () -> Unit) {
        if (!verificarLoggin()) {
            return
        }
        viewModelScope.launch {
            cargando = true
            errorLogin = ""
            try {
                val usuario = usuarioRepository.iniciarSesion(loggin.run, loggin.contrasenia)
                if (usuario != null) {
                    usuarioActual = usuario
                    sesionManager.guardar(usuario.run)
                    loggin.contrasenia = ""
                    onExito()
                } else {
                    errorLogin = "RUN o contraseña incorrectos"
                }
            } catch (e: Exception) {
                errorLogin = "No se pudo iniciar sesión. Intenta nuevamente"
            } finally {
                cargando = false
            }
        }
    }

    fun cerrarSesion() {
        sesionManager.cerrar()
        usuarioActual = null
        loggin.run = ""
        loggin.contrasenia = ""
        errorLogin = ""
    }
}