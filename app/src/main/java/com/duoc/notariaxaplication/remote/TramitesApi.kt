package com.duoc.notariaxaplication.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TramitesApi {

    @GET("tramites")
    suspend fun obtenerTramites(): List<TramiteDto>

    @POST("tramites")
    suspend fun crearTramite(@Body tramite: TramiteNuevoDto): TramiteDto

    @PATCH("tramites/{id}")
    suspend fun actualizarEstado(@Path("id") id: Int, @Body estado: EstadoDto): TramiteDto

    @DELETE("tramites/{id}")
    suspend fun eliminarTramite(@Path("id") id: Int)
}