package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.notariaxaplication.viewmodel.FormularioViewModel
import com.duoc.notariaxaplication.viewmodel.LogginViewModel

private val viewModelLoggin = LogginViewModel()
@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "loggin") {
        composable("loggin") { Loggin(viewModel = viewModelLoggin,
            onOlvidoContrasenia = { /* en proceso */ },
            onIrARegistro = { navController.navigate("register") }
            )
        }
    }
}
