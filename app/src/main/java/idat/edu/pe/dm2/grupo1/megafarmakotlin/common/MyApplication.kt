package idat.edu.pe.dm2.grupo1.megafarmakotlin.common

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    init { INSTANCE = this }

    //Agrupa todos las variables y métodos que están definidos como
    //estáticos
    companion object {
        lateinit var INSTANCE: MyApplication
            private set

        val applicationContext: Context get() { return INSTANCE.applicationContext }
    }
}