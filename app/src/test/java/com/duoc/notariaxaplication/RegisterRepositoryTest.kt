package com.duoc.notariaxaplication

import com.duoc.notariaxaplication.repository.RegisterRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RegisterRepositoryTest : StringSpec({

    {
        val repo = RegisterRepository()
        repo.getRegister().correo = "usuario@notariax.cl"

        repo.validacionCorreo() shouldBe true
    }

    {
        val repo = RegisterRepository()
        repo.getRegister().contrasenia = "123456"
        repo.getRegister().con_contrasenia = "654321"

        repo.validacionConfirmacion() shouldBe false
    }
})