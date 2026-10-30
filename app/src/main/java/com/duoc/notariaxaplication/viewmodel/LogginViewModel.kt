package com.duoc.notariaxaplication.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariaxaplication.repository.FotoStorage
import com.duoc.notariaxaplication.repository.LogginRepository
import com.duoc.notariaxaplication.repository.SesionManager
import com.duoc.notariaxaplication.repository.UsuarioRepository
import com.duoc.notariaxaplication.model.LogginModel
import com.duoc.notariaxaplication.model.MensajeError
import com.duoc.notariaxaplication.model.UsuarioModel
import kotlinx.coroutines.launch

class LogginViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val sesionManager: SesionManager,
    private val fotoStorage: FotoStorage
) : ViewModel() {

    private val repository = LogginRepository()

    var loggin : LogginModel by mutableStateOf( repository.getLoggin())

    var mensajeError: MensajeError by mutableStateOf(repository.getMsError())

    var cargando by mutableStateOf(false)
    var errorLogin by mutableStateOf("")

    var usuarioActual by mutableStateOf<UsuarioModel?>(null)
        private set

    var fotoPerfil by mutableStateOf<Bitmap?>(null)
        private set
    var guardandoFoto by mutableStateOf(false)
        private set
    var errorFoto by mutableStateOf("")

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
                if (usuarioActual == null) sesionManager.cerrar() else cargarFoto()
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
                    cargarFoto()
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
        fotoPerfil = null
        errorFoto = ""
        loggin.run = ""
        loggin.contrasenia = ""
        errorLogin = ""
    }

    private suspend fun cargarFoto() {
        fotoPerfil = usuarioActual?.fotoPerfil?.let { fotoStorage.cargar(it) }
    }

    fun crearUriCamara(): Uri = fotoStorage.crearUriCamara()

    fun guardarFoto(uri: Uri) {
        val usuario = usuarioActual ?: return
        viewModelScope.launch {
            guardandoFoto = true
            errorFoto = ""
            try {
                val rutaNueva = fotoStorage.guardar(uri, usuario.id)
                usuarioRepository.actualizarFoto(usuario.id, rutaNueva)
                usuario.fotoPerfil?.let { fotoStorage.eliminar(it) }
                usuarioActual = usuario.copy(fotoPerfil = rutaNueva)
                cargarFoto()
            } catch (e: Exception) {
                errorFoto = "No se pudo guardar la foto. Intenta nuevamente"
            } finally {
                guardandoFoto = false
            }
        }
    }

    fun eliminarFoto() {
        val usuario = usuarioActual ?: return
        viewModelScope.launch {
            try {
                usuarioRepository.actualizarFoto(usuario.id, null)
                usuario.fotoPerfil?.let { fotoStorage.eliminar(it) }
                usuarioActual = usuario.copy(fotoPerfil = null)
                fotoPerfil = null
            } catch (e: Exception) {
                errorFoto = "No se pudo eliminar la foto"
            }
        }
    }
}
