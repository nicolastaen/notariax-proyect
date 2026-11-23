package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.model.TramiteModel
import com.duoc.notariaxaplication.model.UsuarioModel
import com.duoc.notariaxaplication.repository.TramiteRepository
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.ui.theme.NotariaPlaceholder
import com.duoc.notariaxaplication.viewmodel.TramitesViewModel

@Composable
fun MisTramites(
    usuario: UsuarioModel?,
    viewModel: TramitesViewModel,
    onVolver: () -> Unit
) {
    PantallaTramites(
        titulo = "Mis trámites",
        textoVacio = "Aún no tienes trámites. Crea el primero desde Inicio.",
        modoFirma = false,
        usuario = usuario,
        viewModel = viewModel,
        onVolver = onVolver
    )
}

@Composable
fun FirmarDocumento(
    usuario: UsuarioModel?,
    viewModel: TramitesViewModel,
    onVolver: () -> Unit
) {
    PantallaTramites(
        titulo = "Firmar documento",
        textoVacio = "No tienes documentos pendientes de firma.",
        modoFirma = true,
        usuario = usuario,
        viewModel = viewModel,
        onVolver = onVolver
    )
}

@Composable
private fun PantallaTramites(
    titulo: String,
    textoVacio: String,
    modoFirma: Boolean,
    usuario: UsuarioModel?,
    viewModel: TramitesViewModel,
    onVolver: () -> Unit
) {
    LaunchedEffect(usuario?.id) {
        usuario?.let { viewModel.cargar(it.id) }
    }

    var porConfirmar by remember { mutableStateOf<TramiteModel?>(null) }

    val lista = if (modoFirma) {
        viewModel.tramites.filter { it.estado == TramiteRepository.PENDIENTE }
    } else {
        viewModel.tramites
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .systemBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoVolver(titulo = titulo, onVolver = onVolver)
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.errorAccion.isNotEmpty()) {
            Text(text = viewModel.errorAccion, color = NotariaError, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        when {
            viewModel.cargando && viewModel.tramites.isEmpty() -> {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NotariaDorado)
                }
            }
            viewModel.error.isNotEmpty() -> {
                Text(text = viewModel.error, color = NotariaError, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
                BotonDorado(texto = "Reintentar", onClick = { usuario?.let { viewModel.cargar(it.id) } })
            }
            lista.isEmpty() -> {
                Text(text = textoVacio, color = NotariaPlaceholder, fontSize = 14.sp)
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 24.dp)
                ) {
                    items(lista, key = { it.id }) { tramite ->
                        TarjetaTramite(
                            tramite = tramite,
                            textoAccion = if (modoFirma) "Firmar" else "Cancelar trámite",
                            onAccion = { porConfirmar = tramite }
                        )
                    }
                }
            }
        }
    }

    porConfirmar?.let { tramite ->
        AlertDialog(
            onDismissRequest = { porConfirmar = null },
            containerColor = Color(0xFF242424),
            titleContentColor = Color.White,
            textContentColor = NotariaPlaceholder,
            title = { Text(if (modoFirma) "Firmar documento" else "Cancelar trámite") },
            text = {
                Text(
                    if (modoFirma) "¿Quieres firmar \"${tramite.tipo}\"? Pasará a estado Firmado."
                    else "¿Seguro que quieres cancelar \"${tramite.tipo}\"? Se eliminará de tu lista."
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (modoFirma) viewModel.firmar(tramite) else viewModel.eliminar(tramite)
                    porConfirmar = null
                }) {
                    Text(if (modoFirma) "Firmar" else "Sí, cancelar", color = NotariaDorado)
                }
            },
            dismissButton = {
                TextButton(onClick = { porConfirmar = null }) {
                    Text("Volver", color = NotariaPlaceholder)
                }
            }
        )
    }
}

@Composable
private fun TarjetaTramite(
    tramite: TramiteModel,
    textoAccion: String,
    onAccion: () -> Unit
) {
    val pendiente = tramite.estado == TramiteRepository.PENDIENTE
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF242424))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = tramite.tipo,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            EstadoEtiqueta(tramite.estado)
        }
        if (tramite.descripcion.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = tramite.descripcion, color = NotariaPlaceholder, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "N° ${tramite.id}", color = NotariaPlaceholder, fontSize = 11.sp)
        if (pendiente) {
            TextButton(onClick = onAccion) {
                Text(textoAccion, color = NotariaDorado, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun EstadoEtiqueta(estado: String) {
    val firmado = estado == TramiteRepository.FIRMADO
    val color = if (firmado) Color(0xFF4CAF50) else NotariaDorado
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = estado, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}