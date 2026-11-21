package com.duoc.notariaxaplication.remote

import retrofit2.HttpException
import java.io.IOException

fun Throwable.mensajeDeError(porDefecto: String): String = when (this) {
    is IOException -> "Sin conexión con el servidor. Revisa tu internet"
    is HttpException -> "El servidor respondió con un error (${code()})"
    else -> porDefecto
}