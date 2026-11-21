package com.duoc.notariaxaplication.repository

import android.net.Uri
import com.duoc.notariaxaplication.model.UsuarioDao
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.remote.FotoDto
import com.duoc.notariaxaplication.remote.UsuarioDto
import com.duoc.notariaxaplication.remote.UsuariosApi
import com.duoc.notariaxaplication.remote.aDto
import com.duoc.notariaxaplication.remote.aModelo
import java.security.MessageDigest

// Coordina las tres fuentes de datos:
//  - api (Xano): fuente PRINCIPAL. Aquí viven los usuarios de verdad.
//  - dao (Room): CACHÉ local del usuario con sesión iniciada, para leerlo sin internet.
//  - fotoStorage: archivo de la foto en el teléfono.
class UsuarioRepository(
    private val dao: UsuarioDao,
    private val api: UsuariosApi,
    private val fotoStorage: FotoStorage
) {
    suspend fun registrar(usuario: UsuarioModel): Boolean {
        val run = normalizarRun(usuario.run)
        if (buscarEnApi(run) != null) {
            return false
        }
        api.crearUsuario(
            usuario.copy(run = run, contrasenia = hash(usuario.contrasenia)).aDto()
        )
        return true
    }

    suspend fun iniciarSesion(run: String, contrasenia: String): UsuarioModel? {
        val dto = buscarEnApi(normalizarRun(run)) ?: return null
        if (dto.contrasenia != hash(contrasenia)) return null

        val usuarioBase = dto.aModelo().copy(run = normalizarRun(dto.run.orEmpty()))
        dao.getPorRun(usuarioBase.run)?.fotoPerfil?.let { fotoStorage.eliminar(it) }
        val ruta = dto.fotoPerfil
            ?.takeIf { it.isNotBlank() }
            ?.let { fotoStorage.guardarDesdeBase64(it, usuarioBase.id) }

        val usuario = usuarioBase.copy(fotoPerfil = ruta)
        dao.insert(usuario)
        return usuario
    }

    suspend fun existeRun(run: String): Boolean = buscarEnApi(normalizarRun(run)) != null
    suspend fun obtenerPorRun(run: String): UsuarioModel? = dao.getPorRun(normalizarRun(run))

    suspend fun cambiarFoto(usuario: UsuarioModel, uri: Uri): String {
        val ruta = fotoStorage.guardar(uri, usuario.id)
        try {
            api.actualizarFoto(usuario.id, FotoDto(fotoStorage.aBase64(ruta)))
            dao.actualizarFoto(usuario.id, ruta)
        } catch (e: Exception) {
            fotoStorage.eliminar(ruta)
            throw e
        }
        usuario.fotoPerfil?.let { fotoStorage.eliminar(it) }
        return ruta
    }

    suspend fun quitarFoto(usuario: UsuarioModel) {
        api.actualizarFoto(usuario.id, FotoDto("")) // "" = sin foto
        dao.actualizarFoto(usuario.id, null)
        usuario.fotoPerfil?.let { fotoStorage.eliminar(it) }
    }

    private suspend fun buscarEnApi(run: String): UsuarioDto? =
        api.obtenerUsuarios().firstOrNull { normalizarRun(it.run.orEmpty()) == run }

    private fun normalizarRun(run: String): String =
        run.replace(".", "").trim().uppercase()

    private fun hash(texto: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(texto.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
}