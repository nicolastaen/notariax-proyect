package com.duoc.notariaxaplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsurioModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ap_paterno: String,
    val ap_materno: String,
    val run: String,
    val contrasenia: String,
    val direccion: String)