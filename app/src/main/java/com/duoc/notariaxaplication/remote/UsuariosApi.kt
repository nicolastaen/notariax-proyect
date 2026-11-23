package com.duoc.notariaxaplication.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuariosApi {

    @GET("usuarios")
    suspend fun obtenerUsuarios(): List<UsuarioDto>

    @POST("usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioDto)

    @PATCH("usuarios/{id}")
    suspend fun actualizarFoto(@Path("id") id: Int, @Body foto: FotoDto)
}

object RetrofitClient {
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:LGRziPCT/"

    private fun crearRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun crearApi(baseUrl: String = BASE_URL): UsuariosApi =
        crearRetrofit(baseUrl).create(UsuariosApi::class.java)

    fun crearTramitesApi(baseUrl: String = BASE_URL): TramitesApi =
        crearRetrofit(baseUrl).create(TramitesApi::class.java)
}