package idat.edu.pe.dm2.grupo1.megafarmakotlin.common

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferencesManager {
    private val APP_SETTINGS_FILE = "APP_SETTINGS"

    //guardar informacion
    private fun getSharedPreferences(): SharedPreferences {
        return MyApplication.applicationContext.getSharedPreferences(
            APP_SETTINGS_FILE,
            MODE_PRIVATE
        )
    }

    fun setSomeBooleanValue(dataLabel: String, dataValue: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(dataLabel, dataValue)
        editor.commit()
    }

    fun getSomeBooleanValue(dataLabel: String): Boolean {
        return getSharedPreferences().getBoolean(dataLabel, false)
    }

    fun deletePreferences(dataLabel: String) {
        getSharedPreferences().edit().remove(dataLabel).commit()
    }
}