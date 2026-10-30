package com.duoc.notariaxaplication.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UsuarioModel::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}

