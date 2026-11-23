package com.duoc.notariaxaplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariaxaplication.model.TramiteModel
import com.duoc.notariaxaplication.remote.mensajeDeError
import com.duoc.notariaxaplication.repository.TramiteRepository
import kotlinx.coroutines.launch

class TramitesViewModel(private val repository: TramiteRepository) : ViewModel() {

    var tramites by mutableStateOf<List<TramiteModel>>(emptyList())
        private set
    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf("")
    var errorAccion by mutableStateOf("")
        private set

    var tipoSeleccionado by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var errorTipo by mutableStateOf("")
    var guardando by mutableStateOf(false)
        private set
    var errorCrear by mutableStateOf("")
        private set

    fun verificarTipo(): Boolean {
        if (!repository.validacionTipo(tipoSeleccionado)) {
            errorTipo = "Selecciona el tipo de trámite"
            return false
        }
        errorTipo = ""
        return true
    }

    fun cargar(usuarioId: Int) {
        viewModelScope.launch {
            cargando = true
            error = ""
            try {
                tramites = repository.obtenerDeUsuario(usuarioId)
            } catch (e: Exception) {
                error = e.mensajeDeError("No se pudieron cargar tus trámites")
            } finally {
                cargando = false
            }
        }
    }

    fun crear(usuarioId: Int, onExito: () -> Unit) {
        if (!verificarTipo()) return
        viewModelScope.launch {
            guardando = true
            errorCrear = ""
            try {
                repository.crear(usuarioId, tipoSeleccionado, descripcion)
                tipoSeleccionado = ""
                descripcion = ""
                onExito()
            } catch (e: Exception) {
                errorCrear = e.mensajeDeError("No se pudo crear el trámite. Intenta nuevamente")
            } finally {
                guardando = false
            }
        }
    }

    fun firmar(tramite: TramiteModel) {
        viewModelScope.launch {
            errorAccion = ""
            try {
                repository.firmar(tramite.id)
                tramites = tramites.map {
                    if (it.id == tramite.id) it.copy(estado = TramiteRepository.FIRMADO) else it
                }
            } catch (e: Exception) {
                errorAccion = e.mensajeDeError("No se pudo firmar el documento")
            }
        }
    }

    fun eliminar(tramite: TramiteModel) {
        viewModelScope.launch {
            errorAccion = ""
            try {
                repository.eliminar(tramite.id)
                tramites = tramites.filter { it.id != tramite.id }
            } catch (e: Exception) {
                errorAccion = e.mensajeDeError("No se pudo cancelar el trámite")
            }
        }
    }

    fun limpiar() {
        tramites = emptyList()
        error = ""
        errorAccion = ""
        errorCrear = ""
        tipoSeleccionado = ""
        descripcion = ""
        errorTipo = ""
    }
}