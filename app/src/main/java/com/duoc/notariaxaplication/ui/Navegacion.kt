package com.duoc.notariaxaplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.notariaxaplication.viewmodel.LogginViewModel
import com.duoc.notariaxaplication.viewmodel.RegisterViewModel

@Composable
fun Navegacion(
    logginViewModel: LogginViewModel,
    registerViewModel: RegisterViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "loggin") {

        composable("loggin") {
            Loggin(
                viewModel = logginViewModel,
                onOlvidoContrasenia = { /* en proceso */ },
                onIrARegistro = { navController.navigate("register") },
                onLoginExitoso = {
                    // Entra al perfil y saca el login del historial: "atrás" no vuelve a él.
                    navController.navigate("perfil") {
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

        composable("perfil") {
            PerfilUsuario(
                usuario = logginViewModel.usuarioActual,
                onCerrarSesion = {
                    logginViewModel.cerrarSesion()
                    navController.navigate("loggin") {
                        popUpTo("perfil") { inclusive = true }
                    }
                }
            )
        }
    }
}