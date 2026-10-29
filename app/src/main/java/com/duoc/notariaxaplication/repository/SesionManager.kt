package com.duoc.notariaxaplication.repository

import android.content.Context

class SesionManager(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences("sesion_prefs", Context.MODE_PRIVATE)

    fun guardar(run: String) {
        prefs.edit().putString(CLAVE_RUN, run).apply()
    }

    fun obtenerRun(): String? = prefs.getString(CLAVE_RUN, null)

    fun cerrar() {
        prefs.edit().remove(CLAVE_RUN).apply()
    }

    private companion object {
        const val CLAVE_RUN = "run_sesion"
    }
}
