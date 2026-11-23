package com.duoc.notariaxaplication.repository

import com.duoc.notariaxaplication.model.TramiteModel
import com.duoc.notariaxaplication.remote.EstadoDto
import com.duoc.notariaxaplication.remote.TramiteNuevoDto
import com.duoc.notariaxaplication.remote.TramitesApi
import com.duoc.notariaxaplication.remote.aModelo

class TramiteRepository(private val api: TramitesApi) {

    companion object {
        const val PENDIENTE = "Pendiente"
        const val FIRMADO = "Firmado"

        val TIPOS = listOf(
            "Poder notarial",
            "Declaración jurada",
            "Certificado de firma",
            "Compraventa",
            "Autorización de viaje"
        )
    }

    fun validacionTipo(tipo: String): Boolean = tipo in TIPOS

    suspend fun obtenerDeUsuario(usuarioId: Int): List<TramiteModel> =
        api.obtenerTramites()
            .filter { it.usuarioId == usuarioId }
            .map { it.aModelo() }
            .sortedByDescending { it.id }

    suspend fun crear(usuarioId: Int, tipo: String, descripcion: String): TramiteModel =
        api.crearTramite(TramiteNuevoDto(tipo, descripcion.trim(), PENDIENTE, usuarioId)).aModelo()

    suspend fun firmar(id: Int) {
        api.actualizarEstado(id, EstadoDto(FIRMADO))
    }

    suspend fun eliminar(id: Int) {
        api.eliminarTramite(id)
    }
}