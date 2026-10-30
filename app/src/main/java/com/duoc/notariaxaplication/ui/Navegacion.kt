package com.duoc.notariaxaplication.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    if (logginViewModel.verificandoSesion) {
        Box(
            modifier = Modifier.fillMaxSize().background(NotariaFondo),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = NotariaDorado)
        }
        return
    }

    val inicio = remember { if (logginViewModel.usuarioActual != null) "home" else "loggin" }

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route
    val mostrarBarra = rutaActual == "home" || rutaActual == "perfil"


    Scaffold(
        containerColor = NotariaFondo,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (mostrarBarra) {
                BarraInferior(rutaActual) { ruta ->
                    navController.navigate(ruta) {
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
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {

            composable("loggin") {
                Loggin(
                    viewModel = logginViewModel,
                    onOlvidoContrasenia = { /* en proceso */ },
                    onIrARegistro = { navController.navigate("register") },
                    onLoginExitoso = {
                        navController.navigate("cargando") {
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
                        navController.navigate("loggin") {
                            popUpTo("loggin") { inclusive = true }
                        }
                    }
                )
            }

            composable("cargando") {
                PantallaCarga(
                    onTerminar = {
                        navController.navigate("home") {
                            popUpTo("cargando") { inclusive = true }
                        }
                    }
                )
            }

            composable("home") {
                Home(usuario = logginViewModel.usuarioActual)
            }

            composable("perfil") {
                PerfilUsuario(
                    viewModel = logginViewModel,
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