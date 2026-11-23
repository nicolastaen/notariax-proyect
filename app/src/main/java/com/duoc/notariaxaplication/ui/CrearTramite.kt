package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.repository.TramiteRepository
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.viewmodel.TramitesViewModel

@Composable
fun CrearTramite(
    usuario: UsuarioModel?,
    viewModel: TramitesViewModel,
    onVolver: () -> Unit,
    onCreado: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoVolver(titulo = "Crear trámite", onVolver = onVolver)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tipo de trámite",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        TramiteRepository.TIPOS.forEach { tipo ->
            OpcionTipo(
                texto = tipo,
                seleccionado = viewModel.tipoSeleccionado == tipo,
                onClick = {
                    viewModel.tipoSeleccionado = tipo
                    viewModel.verificarTipo()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (viewModel.errorTipo.isNotEmpty()) {
            Text(text = viewModel.errorTipo, color = NotariaError, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        CampoTexto(
            etiqueta = "Descripción (opcional)",
            valor = viewModel.descripcion,
            alCambiar = { viewModel.descripcion = it },
            placeholder = "ej: Poder para un trámite bancario",
            hayError = false,
            mensajeError = ""
        )

        if (viewModel.errorCrear.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = viewModel.errorCrear, color = NotariaError, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        BotonDorado(
            texto = if (viewModel.guardando) "Creando..." else "Crear trámite",
            habilitado = !viewModel.guardando && usuario != null,
            onClick = { usuario?.let { viewModel.crear(it.id, onCreado) } }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun OpcionTipo(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    val forma = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(if (seleccionado) NotariaDorado.copy(alpha = 0.25f) else Color(0xFF242424))
            .border(1.dp, if (seleccionado) NotariaDorado else Color.Transparent, forma)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun EncabezadoVolver(titulo: String, onVolver: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
        }
        Text(text = titulo, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
    }
}