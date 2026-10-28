package com.duoc.notariaxaplication.model

import androidx.room.*

@Dao
interface UsuarioDao {
    @Query("select * from usuarios")
    suspend fun getAll(): List<UsuarioModel>

    @Query("SELECT EXISTS(SELECT 1 FROM usuarios WHERE run = :run)")
    suspend fun existeRun(run: String): Boolean

    @Query("SELECT * FROM usuarios WHERE run = :run LIMIT 1")
    suspend fun getPorRun(run: String): UsuarioModel?

    @Insert
    suspend fun insert(usuarioModel: UsuarioModel)

    @Update
    suspend fun update(usuarioModel: UsuarioModel)

    @Delete
    suspend fun delete(usuarioModel: UsuarioModel)

}