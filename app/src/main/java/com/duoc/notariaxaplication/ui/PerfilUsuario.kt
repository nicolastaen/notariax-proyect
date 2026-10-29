package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.ui.theme.NotariaPlaceholder

// Pantalla provisoria: muestra los datos del usuario que inició sesión.
// Más adelante aquí se agrega la foto de perfil con la cámara.
@Composable
fun PerfilUsuario(
    usuario: UsuarioModel?,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        EncabezadoNotaria()

        Spacer(modifier = Modifier.height(40.dp))

        if (usuario == null) {
            Text(text = "No hay una sesión activa", color = Color.White)
        } else {
            Text(text = "Bienvenido/a", color = NotariaDorado, fontSize = 14.sp)
            Text(
                text = "${usuario.nombre} ${usuario.ap_paterno}",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            DatoPerfil(etiqueta = "Run", valor = usuario.run)
            DatoPerfil(etiqueta = "Correo electrónico", valor = usuario.correo)
            DatoPerfil(etiqueta = "Dirección", valor = usuario.direccion)
        }

        Spacer(modifier = Modifier.height(32.dp))

        BotonDorado(texto = "Cerrar sesión", onClick = onCerrarSesion)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Un dato del perfil: etiqueta pequeña arriba y el valor debajo.
@Composable
private fun DatoPerfil(etiqueta: String, valor: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = etiqueta, color = NotariaPlaceholder, fontSize = 12.sp)
        Text(text = valor, color = Color.White, fontSize = 16.sp)
    }
}