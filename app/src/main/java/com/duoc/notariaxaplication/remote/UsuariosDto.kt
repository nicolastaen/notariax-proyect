package com.duoc.notariaxaplication.remote

import com.duoc.notariaxaplication.model.UsuarioModel
import com.google.gson.annotations.SerializedName

data class UsuarioDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("ap_paterno") val apPaterno: String? = null,
    @SerializedName("ap_materno") val apMaterno: String? = null,
    @SerializedName("rut") val run: String? = null,
    @SerializedName("contrasenia") val contrasenia: String? = null,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("correo") val correo: String? = null,
    @SerializedName("foto_perfil") val fotoPerfil: String? = null
)

data class FotoDto(
    @SerializedName("foto_perfil") val fotoPerfil: String
)
fun UsuarioDto.aModelo(): UsuarioModel = UsuarioModel(
    id = id ?: 0,
    nombre = nombre.orEmpty(),
    ap_paterno = apPaterno.orEmpty(),
    ap_materno = apMaterno.orEmpty(),
    run = run.orEmpty(),
    contrasenia = contrasenia.orEmpty(),
    direccion = direccion.orEmpty(),
    correo = correo.orEmpty(),
    fotoPerfil = null
)
fun UsuarioModel.aDto(): UsuarioDto = UsuarioDto(
    nombre = nombre,
    apPaterno = ap_paterno,
    apMaterno = ap_materno,
    run = run,
    contrasenia = contrasenia,
    direccion = direccion,
    correo = correo
)