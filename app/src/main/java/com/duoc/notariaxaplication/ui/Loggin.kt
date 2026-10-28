package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.duoc.notariaxaplication.viewmodel.LogginViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.duoc.notariaxaplication.R



@Composable
fun Loggin(viewModel: LogginViewModel) {

    var abrirModal by remember { mutableStateOf(false) }

    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logonotariax),
            contentDescription = "logo",
            modifier = Modifier
                .width(250.dp)
                .height(175.dp)
        )

        OutlinedTextField(
            value = ,
            onValueChange =,
            label = { Text("Ingresa tu run") },
            isError = ,
            supportingText =
        )
    }