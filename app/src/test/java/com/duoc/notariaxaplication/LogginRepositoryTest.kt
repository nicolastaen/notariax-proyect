package com.duoc.notariaxaplication

import com.duoc.notariaxaplication.repository.LogginRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LogginRepositoryTest : StringSpec({

    {
        val repo = LogginRepository()
        repo.getLoggin().run = "12.345.678-5"

        repo.validacionRun() shouldBe true
    }

    {
        val repo = LogginRepository()
        repo.getLoggin().contrasenia = ""

        repo.validacionContrasenia() shouldBe false
    }
})