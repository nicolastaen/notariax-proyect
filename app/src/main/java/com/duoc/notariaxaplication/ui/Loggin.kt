package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.R
import com.duoc.notariaxaplication.ui.theme.NotariaAzulEnlace
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.viewmodel.LogginViewModel


@Composable
fun Loggin(
    viewModel: LogginViewModel,
    onOlvidoContrasenia: () -> Unit,
    onIrARegistro: () -> Unit,
    onLoginExitoso: () -> Unit
) {

    var runTocado by remember { mutableStateOf(false) }
    var contraseniaTocada by remember { mutableStateOf(false) }

    val runValido = viewModel.verificarRun()
    val contraseniaValida = viewModel.verificarContrasenia()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logonotariax),
            contentDescription = "Logo NotariaX",
            modifier = Modifier
                .width(256.dp)
                .height(68.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "aplicación para trámites online",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1.3f))

        CampoTexto(
            etiqueta = "Run",
            valor = viewModel.loggin.run,
            alCambiar = {
                viewModel.loggin.run = it
                viewModel.errorLogin = ""
                runTocado = true
            },
            placeholder = "12.345.678-9",
            hayError = runTocado && !runValido,
            mensajeError = viewModel.mensajeError.run
        )

        Spacer(modifier = Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = viewModel.loggin.contrasenia,
            alCambiar = {
                viewModel.loggin.contrasenia = it
                viewModel.errorLogin = ""
                contraseniaTocada = true
            },
            placeholder = "********",
            hayError = contraseniaTocada && !contraseniaValida,
            mensajeError = viewModel.mensajeError.contrasenia,
            transformacion = PasswordVisualTransformation()
        )

        if (viewModel.errorLogin.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = viewModel.errorLogin,
                color = NotariaError,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BotonDorado(
            texto = if (viewModel.cargando) "Ingresando..." else "Iniciar sesión",
            habilitado = !viewModel.cargando,
            onClick = {
                runTocado = true
                contraseniaTocada = true
                viewModel.iniciarSesion(onExito = onLoginExitoso)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onOlvidoContrasenia) {
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = NotariaAzulEnlace,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "No tienes cuenta?",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = onIrARegistro,
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Regístrate",
                    color = NotariaAzulEnlace,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}