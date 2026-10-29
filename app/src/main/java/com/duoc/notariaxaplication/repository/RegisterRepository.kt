package com.duoc.notariaxaplication.repository

import com.duoc.notariaxaplication.model.DatosPersonalesModel
import com.duoc.notariaxaplication.model.MensajeError
import com.duoc.notariaxaplication.model.RegisterModel
import kotlin.text.matches

class RegisterRepository {

    private val register = RegisterModel()
    private val datosPersonales = DatosPersonalesModel()
    private val errores = MensajeError()

    fun getRegister(): RegisterModel = register
    fun getDatosPersonales(): DatosPersonalesModel = datosPersonales
    fun getMsError(): MensajeError = errores

    // Register

    fun validacionCorreo(): Boolean =
        register.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))

    fun validacionContrasenia(): Boolean =
        register.contrasenia.length >= 4

    fun validacionConfirmacion(): Boolean =
        register.con_contrasenia.isNotEmpty() &&
                register.con_contrasenia == register.contrasenia

    // Datos Personales

    fun validacionNombre(): Boolean = datosPersonales.nombre.isNotBlank()

    fun validacionApPaterno(): Boolean = datosPersonales.ap_paterno.isNotBlank()

    fun validacionApMaterno(): Boolean = datosPersonales.ap_materno.isNotBlank()

    fun validacionRun(): Boolean = datosPersonales.run.matches(Regex("^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]\$"))

    fun validacionDireccion(): Boolean = datosPersonales.direccion.isNotBlank()
}