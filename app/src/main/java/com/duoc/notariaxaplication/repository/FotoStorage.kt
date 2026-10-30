package com.duoc.notariaxaplication.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FotoStorage(context: Context) {

    private val context = context.applicationContext

    fun crearUriCamara(): Uri {
        val carpeta = File(context.cacheDir, "camara").apply { mkdirs() }
        val archivo = File(carpeta, "foto_temp.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
    }

    suspend fun guardar(uri: Uri, idUsuario: Int): String = withContext(Dispatchers.IO) {
        val resolver = context.contentResolver

        val medidas = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, medidas) }

        var muestra = 1
        while (medidas.outWidth / (muestra * 2) >= LADO_MAXIMO &&
            medidas.outHeight / (muestra * 2) >= LADO_MAXIMO
        ) muestra *= 2
        val opciones = BitmapFactory.Options().apply { inSampleSize = muestra }
        val original = resolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, opciones)
        } ?: throw IOException("No se pudo leer la imagen")

        val orientacion = resolver.openInputStream(uri)?.use {
            ExifInterface(it).getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
            )
        } ?: ExifInterface.ORIENTATION_NORMAL
        val grados = when (orientacion) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }
        val imagen: Bitmap = if (grados == 0f) original else {
            val matriz = Matrix().apply { postRotate(grados) }
            Bitmap.createBitmap(original, 0, 0, original.width, original.height, matriz, true)
        }

        val carpeta = File(context.filesDir, "fotos").apply { mkdirs() }
        val archivo = File(carpeta, "perfil_${idUsuario}_${System.currentTimeMillis()}.jpg")
        FileOutputStream(archivo).use { imagen.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        archivo.absolutePath
    }

    suspend fun cargar(ruta: String): Bitmap? = withContext(Dispatchers.IO) {
        BitmapFactory.decodeFile(ruta)
    }

    fun eliminar(ruta: String) {
        File(ruta).delete()
    }

    private companion object {
        const val LADO_MAXIMO = 1024
    }
}