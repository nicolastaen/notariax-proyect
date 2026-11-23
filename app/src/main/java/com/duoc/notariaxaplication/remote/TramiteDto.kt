package com.duoc.notariaxaplication.remote

import com.duoc.notariaxaplication.model.TramiteModel
import com.google.gson.annotations.SerializedName

data class TramiteDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("tipo") val tipo: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("estado") val estado: String? = null,
    @SerializedName("usuario_id") val usuarioId: Int? = null
)

data class TramiteNuevoDto(
    @SerializedName("tipo") val tipo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("usuario_id") val usuarioId: Int
)

data class EstadoDto(
    @SerializedName("estado") val estado: String
)

fun TramiteDto.aModelo(): TramiteModel = TramiteModel(
    id = id ?: 0,
    tipo = tipo.orEmpty(),
    descripcion = descripcion.orEmpty(),
    estado = estado.orEmpty(),
    usuarioId = usuarioId ?: 0
)