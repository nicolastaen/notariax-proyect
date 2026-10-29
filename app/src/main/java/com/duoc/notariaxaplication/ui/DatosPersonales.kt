package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.viewmodel.RegisterViewModel
@Composable
fun DatosPersonales(
    viewModel: RegisterViewModel,
    onRegistroExitoso: () -> Unit
) {
    var mostrarErrores by remember { mutableStateOf(false) }
    var mostrarExito by remember { mutableStateOf(false) }

    val nombreOk = viewModel.verificarNombre()
    val paternoOk = viewModel.verificarApPaterno()
    val maternoOk = viewModel.verificarApMaterno()
    val runOk = viewModel.verificarRun()
    val direccionOk = viewModel.verificarDireccion()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        EncabezadoNotaria()

        Spacer(modifier = Modifier.height(32.dp))

        CampoTexto(
            etiqueta = "Nombres",
            valor = viewModel.datosPersonales.nombre,
            alCambiar = { viewModel.datosPersonales.nombre = it },
            placeholder = "ej: Karina Belen",
            hayError = mostrarErrores && !nombreOk,
            mensajeError = viewModel.mensajeError.nombre
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Apellido paterno",
            valor = viewModel.datosPersonales.ap_paterno,
            alCambiar = { viewModel.datosPersonales.ap_paterno = it },
            placeholder = "ej: Tapia",
            hayError = mostrarErrores && !paternoOk,
            mensajeError = viewModel.mensajeError.ap_paterno
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Apellido materno",
            valor = viewModel.datosPersonales.ap_materno,
            alCambiar = { viewModel.datosPersonales.ap_materno = it },
            placeholder = "ej: Alba",
            hayError = mostrarErrores && !maternoOk,
            mensajeError = viewModel.mensajeError.ap_materno
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Run",
            valor = viewModel.datosPersonales.run,
            alCambiar = { viewModel.datosPersonales.run = it },
            placeholder = "ej: 99.999.999-9",
            hayError = mostrarErrores && !runOk,
            mensajeError = viewModel.mensajeError.run
        )

        Spacer(modifier = Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Dirección",
            valor = viewModel.datosPersonales.direccion,
            alCambiar = { viewModel.datosPersonales.direccion = it },
            placeholder = "ej: Alicante 6314",
            hayError = mostrarErrores && !direccionOk,
            mensajeError = viewModel.mensajeError.direccion
        )

        if (viewModel.errorRegistro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = viewModel.errorRegistro,
                color = NotariaError,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        BotonDorado(
            texto = if (viewModel.registrando) "Registrando..." else "Registrarse",
            habilitado = !viewModel.registrando,
            onClick = {
                mostrarErrores = true
                viewModel.registrar(onExito = {
                    mostrarErrores = false
                    mostrarExito = true
                })
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (mostrarExito) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Registro exitoso") },
            text = { Text("Tu cuenta fue creada. Ahora puedes iniciar sesión.") },
            confirmButton = {
                Button(onClick = {
                    mostrarExito = false
                    onRegistroExitoso()
                }) { Text("OK") }
            }
        )
    }
}