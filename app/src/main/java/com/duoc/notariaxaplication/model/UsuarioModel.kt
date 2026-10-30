package com.duoc.notariaxaplication.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
@Entity(tableName = "usuarios",
    indices = [Index(value = ["run"], unique = true)]
)
data class UsuarioModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ap_paterno: String,
    val ap_materno: String,
    val run: String,
    val contrasenia: String,
    val direccion: String,
    val correo: String,
    val fotoPerfil: String? = null
)