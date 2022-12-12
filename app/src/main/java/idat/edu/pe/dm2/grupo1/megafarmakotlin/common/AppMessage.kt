package idat.edu.pe.dm2.grupo1.megafarmakotlin.common

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R

object AppMessage {
    fun enviarMensaje(vista: View, mensaje: String, tipo: TypeMessage) {
        val snackbar = Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG)

        val snackBarView: View = snackbar.view

        when (tipo) {
            TypeMessage.DANGER -> {
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(MyApplication.INSTANCE, R.color.alertDANGER)
                )
            }

            TypeMessage.INFO -> {
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(MyApplication.INSTANCE, R.color.alertINFO)
                )
            }

            else -> {
                snackBarView.setBackgroundColor(
                    ContextCompat.getColor(MyApplication.INSTANCE, R.color.alertSUCCESS)
                )
            }
        }

        snackbar.show()
    }
}