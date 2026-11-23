package com.duoc.notariaxaplication.repository

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class LogginRepositoryTest {

    @Test
    fun `acepta un run con puntos y guion`() {
        println("INICIO: comprobar que un RUN con formato válido es aceptado.")
        println("PREPARACION: se crea el repositorio y se escribe el RUN 12.345.678-5.")
        val repo = LogginRepository()
        repo.getLoggin().run = "12.345.678-5"

        println("ACCION: validar el RUN.")
        val resultado = repo.validacionRun()

        println("COMPROBACION: el resultado debe ser true.")
        assertTrue(resultado)
        println("CORRECTO: el RUN con formato válido fue aceptado.")
    }

    @Test
    fun `rechaza una contrasenia vacia`() {
        println("INICIO: comprobar que la contraseña vacía es rechazada.")
        println("PREPARACION: la contraseña queda como texto vacío.")
        val repo = LogginRepository()
        repo.getLoggin().contrasenia = ""

        println("ACCION: validar la contraseña.")
        val resultado = repo.validacionContrasenia()

        println("COMPROBACION: el resultado debe ser false.")
        assertFalse(resultado)
        println("CORRECTO: la contraseña vacía fue rechazada.")
    }
}