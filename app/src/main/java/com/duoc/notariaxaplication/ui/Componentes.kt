package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.notariaxaplication.R
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaError
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.ui.theme.NotariaPlaceholder

// Campo de texto con la etiqueta arriba (como en el prototipo).
// Se reutiliza en las distintas pantallas para no repetir el estilo.
@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String,
    hayError: Boolean,
    mensajeError: String,
    transformacion: VisualTransformation = VisualTransformation.None,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = etiqueta,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text(placeholder, color = NotariaPlaceholder) },
            visualTransformation = transformacion,
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            isError = hayError,
            supportingText = if (hayError) {
                { Text(mensajeError, color = NotariaError) }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedTextColor = NotariaFondo,
                unfocusedTextColor = NotariaFondo,
                errorTextColor = NotariaFondo,
                cursorColor = NotariaDorado,
                focusedBorderColor = NotariaDorado,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = NotariaError
            )
        )
    }
}

// Botón principal dorado con texto blanco.
@Composable
fun BotonDorado(
    texto: String,
    onClick: () -> Unit,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = NotariaDorado,
            contentColor = Color.White,
            disabledContainerColor = NotariaDorado.copy(alpha = 0.5f),
            disabledContentColor = Color.White
        )
    ) {
        Text(texto, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EncabezadoNotaria() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logonotariax),
            contentDescription = "Logo NotariaX",
            modifier = Modifier
                .width(240.dp)
                .height(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "aplicación para trámites online",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}