package com.duoc.notariaxaplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.notariaxaplication.model.DatosPersonalesModel
import com.duoc.notariaxaplication.model.MensajeError
import com.duoc.notariaxaplication.model.RegisterModel
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.repository.RegisterRepository
import com.duoc.notariaxaplication.repository.UsuarioRepository
import kotlinx.coroutines.launch
class RegisterViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {
    private val repository = RegisterRepository()

    var register: RegisterModel by mutableStateOf(repository.getRegister())
    var datosPersonales: DatosPersonalesModel by mutableStateOf(repository.getDatosPersonales())
    var mensajeError: MensajeError by mutableStateOf(repository.getMsError())

    var registrando by mutableStateOf(false)
    var errorRegistro by mutableStateOf("")

    //register

    fun verificarCorreo(): Boolean {
        if (!repository.validacionCorreo()) {
            mensajeError.correo = "El correo no es válido"
            return false
        } else {
            mensajeError.correo = ""
            return true
        }
    }

    fun verificarContrasenia(): Boolean {
        if (!repository.validacionContrasenia()) {
            mensajeError.contrasenia = "La contraseña debe tener al menos 6 caracteres"
            return false
        } else {
            mensajeError.contrasenia = ""
            return true
        }
    }

    fun verificarConfirmacion(): Boolean {
        if (!repository.validacionConfirmacion()) {
            mensajeError.con_contrasenia = "Las contraseñas no coinciden"
            return false
        } else {
            mensajeError.con_contrasenia = ""
            return true
        }
    }

    fun verificarCuenta(): Boolean {
        val correoOk = verificarCorreo()
        val contraseniaOk = verificarContrasenia()
        val confirmacionOk = verificarConfirmacion()
        return correoOk && contraseniaOk && confirmacionOk
    }

    // datos personales

    fun verificarNombre(): Boolean {
        if (!repository.validacionNombre()) {
            mensajeError.nombre = "Ingresa tus nombres"
            return false
        } else {
            mensajeError.nombre = ""
            return true
        }
    }

    fun verificarApPaterno(): Boolean {
        if (!repository.validacionApPaterno()) {
            mensajeError.ap_paterno = "Ingresa tu apellido paterno"
            return false
        } else {
            mensajeError.ap_paterno = ""
            return true
        }
    }

    fun verificarApMaterno(): Boolean {
        if (!repository.validacionApMaterno()) {
            mensajeError.ap_materno = "Ingresa tu apellido materno"
            return false
        } else {
            mensajeError.ap_materno = ""
            return true
        }
    }

    fun verificarRun(): Boolean {
        if (!repository.validacionRun()) {
            mensajeError.run = "El RUN no es válido (ej: 12.345.678-5)"
            return false
        } else {
            mensajeError.run = ""
            return true
        }
    }

    fun verificarDireccion(): Boolean {
        if (!repository.validacionDireccion()) {
            mensajeError.direccion = "Ingresa tu dirección"
            return false
        } else {
            mensajeError.direccion = ""
            return true
        }
    }

    fun verificarDatosPersonales(): Boolean {
        val nombreOk = verificarNombre()
        val paternoOk = verificarApPaterno()
        val maternoOk = verificarApMaterno()
        val runOk = verificarRun()
        val direccionOk = verificarDireccion()
        return nombreOk && paternoOk && maternoOk && runOk && direccionOk
    }

    // guardar

    fun registrar(onExito: () -> Unit) {
        if (!verificarCuenta() || !verificarDatosPersonales()) {
            return
        }
        viewModelScope.launch {
            registrando = true
            errorRegistro = ""
            try {
                val usuario = UsuarioModel(
                    nombre = datosPersonales.nombre.trim(),
                    ap_paterno = datosPersonales.ap_paterno.trim(),
                    ap_materno = datosPersonales.ap_materno.trim(),
                    run = datosPersonales.run,
                    contrasenia = register.contrasenia,
                    direccion = datosPersonales.direccion.trim(),
                    correo = register.correo.trim()
                )
                if (usuarioRepository.registrar(usuario)) {
                    limpiar()
                    onExito()
                } else {
                    errorRegistro = "Este RUN ya está registrado"
                }
            } catch (e: Exception) {
                errorRegistro = "No se pudo registrar. Intenta nuevamente"
            } finally {
                registrando = false
            }
        }
    }

    // vaciar campos
    private fun limpiar() {
        register.correo = ""
        register.contrasenia = ""
        register.con_contrasenia = ""
        datosPersonales.nombre = ""
        datosPersonales.ap_paterno = ""
        datosPersonales.ap_materno = ""
        datosPersonales.run = ""
        datosPersonales.direccion = ""
    }
}