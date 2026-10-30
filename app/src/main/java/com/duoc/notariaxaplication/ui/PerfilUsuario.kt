package com.duoc.notariaxaplication.ui

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.ui.theme.NotariaPlaceholder
import com.duoc.notariaxaplication.viewmodel.LogginViewModel

@Composable
fun PerfilUsuario(
    viewModel: LogginViewModel,
    onCerrarSesion: () -> Unit
) {
    val usuario = viewModel.usuarioActual
    val foto = viewModel.fotoPerfil

    var mostrarOpciones by rememberSaveable { mutableStateOf(false) }
    var uriCamara by rememberSaveable { mutableStateOf<Uri?>(null) }


    val lanzadorCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { tomada ->
        if (tomada) uriCamara?.let { viewModel.guardarFoto(it) }
    }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.guardarFoto(it) }
    }

    val permisoGaleria = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val pedirPermisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            try {
                val uri = viewModel.crearUriCamara()
                uriCamara = uri
                lanzadorCamara.launch(uri)
            } catch (e: Exception) {
                viewModel.errorFoto = "No se pudo abrir la cámara"
            }
        } else {
            viewModel.errorFoto = "Sin permiso de cámara no se puede tomar la foto"
        }
    }

    val pedirPermisoGaleria = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            lanzadorGaleria.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            viewModel.errorFoto = "Sin permiso de galería no se puede elegir una foto"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NotariaFondo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        EncabezadoNotaria()

        Spacer(modifier = Modifier.height(24.dp))

        // Círculo de la foto
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A))
                .border(1.dp, NotariaDorado.copy(alpha = 0.5f), CircleShape)
                .clickable { mostrarOpciones = true },
            contentAlignment = Alignment.Center
        ) {
            if (foto != null) {
                Image(
                    bitmap = foto.asImageBitmap(),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop, // llena el círculo recortando lo que sobra
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = NotariaPlaceholder,
                        modifier = Modifier.size(48.dp)
                    )
                    Text("Sube tu imagen aquí", color = NotariaPlaceholder, fontSize = 12.sp)
                }
            }
        }

        if (viewModel.guardandoFoto) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Guardando foto...", color = NotariaPlaceholder, fontSize = 12.sp)
        }

        if (foto != null) {
            TextButton(onClick = { viewModel.eliminarFoto() }) {
                Icon(Icons.Default.Delete, contentDescription = null, tint = NotariaError)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Eliminar imagen", color = NotariaError, fontSize = 13.sp)
            }
        }

        if (viewModel.errorFoto.isNotEmpty()) {
            Text(viewModel.errorFoto, color = NotariaError, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título de sección con la barra dorada del prototipo.
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(16.dp)
                    .background(NotariaDorado)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Datos personales", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (usuario == null) {
            Text("No hay una sesión activa", color = Color.White)
        } else {
            CampoLectura("Nombres", usuario.nombre)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoLectura("Apellido Paterno", usuario.ap_paterno, Modifier.weight(1f))
                CampoLectura("Apellido Materno", usuario.ap_materno, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoLectura("RUN", usuario.run, Modifier.weight(1f))
                CampoLectura("Correo electrónico", usuario.correo, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            CampoLectura("Dirección", usuario.direccion)
        }

        Spacer(modifier = Modifier.height(32.dp))

        BotonDorado(texto = "Cerrar sesión", onClick = onCerrarSesion)

        Spacer(modifier = Modifier.height(24.dp))
    }

    if (mostrarOpciones) {
        AlertDialog(
            onDismissRequest = { mostrarOpciones = false },
            containerColor = Color(0xFF242424),
            titleContentColor = Color.White,
            title = { Text("Foto de perfil") },
            text = {
                Column {
                    TextButton(onClick = {
                        mostrarOpciones = false
                        viewModel.errorFoto = ""
                        pedirPermisoCamara.launch(Manifest.permission.CAMERA)
                    }) { Text("Tomar foto", color = NotariaDorado) }

                    TextButton(onClick = {
                        mostrarOpciones = false
                        viewModel.errorFoto = ""
                        pedirPermisoGaleria.launch(permisoGaleria)
                    }) { Text("Elegir de la galería", color = NotariaDorado) }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarOpciones = false }) {
                    Text("Cancelar", color = NotariaPlaceholder)
                }
            }
        )
    }
}

@Composable
private fun CampoLectura(etiqueta: String, valor: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = etiqueta, color = NotariaDorado, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = valor,
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}