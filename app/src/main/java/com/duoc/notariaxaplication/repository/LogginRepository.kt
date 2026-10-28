package com.duoc.notariaxaplication.repository

import com.duoc.notariaxaplication.model.LogginModel
import com.duoc.notariaxaplication.model.MensajeError

class LogginRepository {

    private var loggin = LogginModel()
    private var msError = MensajeError()

    fun getLoggin(): LogginModel = loggin
    fun getMsError(): MensajeError = msError

    fun run(run: String){
        loggin.run = run
    }

    fun validacionRun(): Boolean {
        if(!loggin.run.matches(Regex("^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]\$")))
            return false
        else
            return true
        }

    fun validacionContrasenia(): Boolean{
        if(loggin.contrasenia=="")
            return false
        else
            return true
    }

    }