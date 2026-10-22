package com.duoc.notariaxaplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.duoc.notariaxaplication.ui.Animacion
import com.duoc.notariaxaplication.ui.Formulario
import com.duoc.notariaxaplication.ui.theme.NotariaXaplicationTheme
import com.duoc.notariaxaplication.viewmodel.FormularioViewModel

class MainActivity : ComponentActivity() {

    private val viewModelForm = FormularioViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotariaXaplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Formulario(viewModelForm)
                }
            }
        }
    }
}