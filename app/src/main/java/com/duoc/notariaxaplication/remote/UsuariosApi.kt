package com.duoc.notariaxaplication.remote

import com.duoc.notariaxaplication.model.UsuarioModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UsuariosApi {
    @GET("usuarios")
    suspend fun obtenerUsuarios(): List<UsuarioModel>
}

object RetrofitClient {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:LGRziPCT"

    fun crearApi(baseUrl: String = BASE_URL): UsuariosApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuariosApi::class.java)
}