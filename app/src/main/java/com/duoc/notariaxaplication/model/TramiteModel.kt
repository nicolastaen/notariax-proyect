package com.duoc.notariaxaplication.model

data class TramiteModel(
    val id: Int,
    val tipo: String,
    val descripcion: String,
    val estado: String,
    val usuarioId: Int
)