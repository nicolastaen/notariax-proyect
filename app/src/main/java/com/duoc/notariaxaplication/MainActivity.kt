package com.duoc.notariaxaplication

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.duoc.notariaxaplication.model.AppDatabase
import com.duoc.notariaxaplication.repository.UsuarioRepository
import com.duoc.notariaxaplication.ui.Navegacion
import com.duoc.notariaxaplication.ui.theme.NotariaXaplicationTheme
import com.duoc.notariaxaplication.viewmodel.LogginViewModel
import com.duoc.notariaxaplication.viewmodel.RegisterViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notariax_db"
        ).build()
    }
    private val usuarioRepository by lazy { UsuarioRepository(db.usuarioDao()) }
    private val logginViewModel by lazy { LogginViewModel(usuarioRepository) }
    private val registerViewModel by lazy { RegisterViewModel(usuarioRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            NotariaXaplicationTheme {
                Navegacion(logginViewModel,registerViewModel)
            }
        }
    }
}