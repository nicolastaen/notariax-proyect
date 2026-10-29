package com.duoc.notariaxaplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.duoc.notariaxaplication.ui.theme.NotariaDorado
import com.duoc.notariaxaplication.ui.theme.NotariaFondo
import com.duoc.notariaxaplication.viewmodel.LogginViewModel
import com.duoc.notariaxaplication.viewmodel.RegisterViewModel

@Composable
fun Navegacion(
    logginViewModel: LogginViewModel,
    registerViewModel: RegisterViewModel
) {
    val navController = rememberNavController()

    // startDestination se lee solo una vez, así que primero hay que saber si hay sesión guardada.
    if (logginViewModel.verificandoSesion) {
        Box(
            modifier = Modifier.fillMaxSize().background(NotariaFondo),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = NotariaDorado)
        }
        return
    }

    val inicio = if (logginViewModel.usuarioActual != null) "home" else "loggin"

    // Ruta en la que está el usuario ahora; se actualiza sola en cada navegación.
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val mostrarBarra = rutaActual == "home" || rutaActual == "perfil"

    // Scaffold = estructura base: reserva el espacio de la barra inferior y entrega
    // en innerPadding cuánto espacio ocupa. Los insets (barras del sistema) los manejan
    // las propias pantallas, por eso se desactivan aquí con WindowInsets(0, 0, 0, 0).
    Scaffold(
        containerColor = NotariaFondo,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (mostrarBarra) {
                BarraInferior(rutaActual) { ruta ->
                    navController.navigate(ruta) {
                        // Mantiene "home" como base: atrás desde Perfil vuelve a Inicio,
                        // y launchSingleTop evita apilar la misma pantalla dos veces.
                        popUpTo("home")
                        launchSingleTop = true
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = inicio,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("loggin") {
                Loggin(
                    viewModel = logginViewModel,
                    onOlvidoContrasenia = { /* en proceso */ },
                    onIrARegistro = { navController.navigate("register") },
                    onLoginExitoso = {
                        // Entra al home y saca el login del historial: "atrás" no vuelve a él.
                        navController.navigate("home") {
                            popUpTo("loggin") { inclusive = true }
                        }
                    }
                )
            }

            composable("register") {
                Register(
                    viewModel = registerViewModel,
                    onContinuar = { navController.navigate("datosPersonales") }
                )
            }

            composable("datosPersonales") {
                DatosPersonales(
                    viewModel = registerViewModel,
                    onRegistroExitoso = {
                        // Vuelve al login y borra del historial las pantallas de registro.
                        navController.navigate("loggin") {
                            popUpTo("loggin") { inclusive = true }
                        }
                    }
                )
            }

            composable("home") {
                Home(usuario = logginViewModel.usuarioActual)
            }

            composable("perfil") {
                PerfilUsuario(
                    usuario = logginViewModel.usuarioActual,
                    onCerrarSesion = {
                        logginViewModel.cerrarSesion()
                        navController.navigate("loggin") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}