package com.duoc.notariaxaplication.repository

import com.duoc.notariaxaplication.model.UsuarioDao
import com.duoc.notariaxaplication.model.UsuarioModel
import java.security.MessageDigest

class UsuarioRepository(private val dao: UsuarioDao) {

    suspend fun registrar(usuario: UsuarioModel): Boolean {
        val run = normalizarRun(usuario.run)
        if (dao.existeRun(run)) {
            return false
        }
        dao.insert(
            usuario.copy(
                run = run,
                contrasenia = hash(usuario.contrasenia)
            )
        )
        return true
    }

    suspend fun iniciarSesion(run: String, contrasenia: String): UsuarioModel? {
        val usuario = dao.getPorRun(normalizarRun(run)) ?: return null
        return if (usuario.contrasenia == hash(contrasenia)) usuario else null
    }

    suspend fun existeRun(run: String): Boolean = dao.existeRun(normalizarRun(run))

    suspend fun obtenerPorRun(run: String): UsuarioModel? = dao.getPorRun(normalizarRun(run))

    private fun normalizarRun(run: String): String =
        run.replace(".", "").trim().uppercase()

    private fun hash(texto: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(texto.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
}