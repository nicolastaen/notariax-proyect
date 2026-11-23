package com.duoc.notariaxaplication.repository

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegisterRepositoryTest {

    @Test
    fun `acepta un correo valido`() {
        println("INICIO: comprobar que un correo con formato válido es aceptado.")
        println("PREPARACION: se escribe usuario@notariax.cl.")
        val repo = RegisterRepository()
        repo.getRegister().correo = "usuario@notariax.cl"

        println("ACCION: validar el correo.")
        val resultado = repo.validacionCorreo()

        println("COMPROBACION: el resultado debe ser true.")
        assertTrue(resultado)
        println("CORRECTO: el correo válido fue aceptado.")
    }

    @Test
    fun `rechaza una confirmacion distinta a la contrasenia`() {
        println("INICIO: comprobar que la confirmación debe coincidir con la contraseña.")
        println("PREPARACION: contraseña 123456 y confirmación 654321.")
        val repo = RegisterRepository()
        repo.getRegister().contrasenia = "123456"
        repo.getRegister().con_contrasenia = "654321"

        println("ACCION: validar la confirmación.")
        val resultado = repo.validacionConfirmacion()

        println("COMPROBACION: el resultado debe ser false.")
        assertFalse(resultado)
        println("CORRECTO: la confirmación distinta fue rechazada.")
    }
}