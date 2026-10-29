package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.ui.theme.NotariaPlaceholder

// Pantalla principal según el prototipo. Los botones aún no tienen función.
// La barra inferior la pone Navegacion (Scaffold), por eso aquí no aparece.
@Composable
fun Home(usuario: UsuarioModel?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hola, ${usuario?.nombre ?: "Usuario"}",
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "EL SERVICIO DIGITAL QUE SE ADAPTA A TI.",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Olvídate de filas y papeleo.\nCompleta tu gestión online en 3 pasos fácil y con respaldo legal garantizado",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonDorado(texto = "iniciar trámite ahora →", onClick = { /* en proceso */ })

        Spacer(modifier = Modifier.height(32.dp))

        TarjetaAccion(Icons.Default.Add, "Crear trámite", "Nuevo documento o certificado") { /* en proceso */ }
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaAccion(Icons.AutoMirrored.Filled.List, "Mis trámites", "Revisa el estado de tus gestiones") { /* en proceso */ }
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaAccion(Icons.Default.Edit, "Firmar documento", "Simple, avanzada o notariada") { /* en proceso */ }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Tarjeta con ícono, título, descripción y flecha. clickable la vuelve un botón.
@Composable
private fun TarjetaAccion(
    icono: ImageVector,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF242424))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null, tint = NotariaDorado)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(text = titulo, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = descripcion, color = NotariaPlaceholder, fontSize = 12.sp)
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NotariaPlaceholder)
    }
}