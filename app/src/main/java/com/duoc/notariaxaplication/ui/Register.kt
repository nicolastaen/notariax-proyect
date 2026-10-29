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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.viewmodel.RegisterViewModel

@Composable
fun Register(
    viewModel: RegisterViewModel,
    onContinuar: () -> Unit
) {
    var mostrarErrores by remember { mutableStateOf(false) }

    val correoOk = viewModel.verificarCorreo()
    val contraseniaOk = viewModel.verificarContrasenia()
    val confirmacionOk = viewModel.verificarConfirmacion()

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

        Spacer(modifier = Modifier.height(56.dp))

        CampoTexto(
            etiqueta = "Correo electrónico",
            valor = viewModel.register.correo,
            alCambiar = { viewModel.register.correo = it },
            placeholder = "your.email@example.com",
            hayError = mostrarErrores && !correoOk,
            mensajeError = viewModel.mensajeError.correo,
            tipoTeclado = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = viewModel.register.contrasenia,
            alCambiar = { viewModel.register.contrasenia = it },
            placeholder = "••••••••",
            hayError = mostrarErrores && !contraseniaOk,
            mensajeError = viewModel.mensajeError.contrasenia,
            transformacion = PasswordVisualTransformation(),
            tipoTeclado = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Confirmar contraseña",
            valor = viewModel.register.con_contrasenia,
            alCambiar = { viewModel.register.con_contrasenia = it },
            placeholder = "••••••••",
            hayError = mostrarErrores && !confirmacionOk,
            mensajeError = viewModel.mensajeError.con_contrasenia,
            transformacion = PasswordVisualTransformation(),
            tipoTeclado = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(48.dp))

        BotonDorado(
            texto = "Continuar",
            onClick = {
                mostrarErrores = true
                if (viewModel.verificarCuenta()) {
                    onContinuar()
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
