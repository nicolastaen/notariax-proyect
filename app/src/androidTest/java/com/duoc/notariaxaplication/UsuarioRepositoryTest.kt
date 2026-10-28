package com.duoc.notariaxaplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.duoc.notariaxaplication.model.AppDatabase
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.repository.UsuarioRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsuarioRepositoryTest {

    private lateinit var db: AppDatabase
    private lateinit var repo: UsuarioRepository

    private fun usuario(run: String = "12.345.678-k") = UsuarioModel(
        nombre = "Ana",
        ap_paterno = "Pérez",
        ap_materno = "Soto",
        run = run,
        contrasenia = "clave123",
        direccion = "Calle 1",
        correo = "ana@correo.cl"
    )

    @Before
    fun crearBase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Base de datos solo en memoria: se borra al terminar cada test.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        repo = UsuarioRepository(db.usuarioDao())
    }

    @After
    fun cerrarBase() {
        db.close()
    }

    @Test
    fun registrarGuardaElRunNormalizadoYLaContraseniaConHash() = runBlocking {
        assertTrue(repo.registrar(usuario("12.345.678-k")))

        val guardado = db.usuarioDao().getPorRun("12345678-K")
        assertNotNull(guardado)
        assertNotEquals("clave123", guardado!!.contrasenia)
        assertEquals(64, guardado.contrasenia.length)
    }

    @Test
    fun noPermiteRegistrarDosVecesElMismoRun() = runBlocking {
        assertTrue(repo.registrar(usuario("12345678-9")))
        assertFalse(repo.registrar(usuario("12.345.678-9")))
        assertEquals(1, db.usuarioDao().getAll().size)
    }

    @Test
    fun iniciarSesionFuncionaConLaContraseniaCorrecta() = runBlocking {
        repo.registrar(usuario("12345678-9"))

        assertNotNull(repo.iniciarSesion("12.345.678-9", "clave123"))
    }

    @Test
    fun iniciarSesionFallaConContraseniaOUsuarioIncorrecto() = runBlocking {
        repo.registrar(usuario("12345678-9"))

        assertNull(repo.iniciarSesion("12345678-9", "otraClave"))
        assertNull(repo.iniciarSesion("99999999-9", "clave123"))
    }
}
