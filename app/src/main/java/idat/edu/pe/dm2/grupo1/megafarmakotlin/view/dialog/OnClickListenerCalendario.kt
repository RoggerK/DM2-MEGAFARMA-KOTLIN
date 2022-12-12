package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.dialog

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import java.time.ZoneId
import java.time.ZonedDateTime

class OnClickListenerCalendario(var medicamento: MedicamentoResponse) : View.OnClickListener {

    private lateinit var context: Context

    override fun onClick(view: View) {
        context = view.context

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val formDialogCalendario: View =
            inflater.inflate(R.layout.item_dialog_calendario, null, false)

        val radgCalRecordar: RadioGroup = formDialogCalendario.findViewById(R.id.radgCalRecordar)
        val radbUnaSemana: RadioButton = formDialogCalendario.findViewById(R.id.radbUnaSemana)
        val radbQuinceDias: RadioButton = formDialogCalendario.findViewById(R.id.radbQuinceDias)
        val radbUnMes: RadioButton = formDialogCalendario.findViewById(R.id.radbUnMes)

        MaterialAlertDialogBuilder(context)
            .setView(formDialogCalendario)
            .setTitle(medicamento.nombre_producto)
            .setMessage(context.getString(R.string.valMenCalendario))
            .setNeutralButton(context.getString(R.string.Salir)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(context.getString(R.string.valAgendar)) { dialog, which ->
                val dateTime = ZonedDateTime.now(ZoneId.of("America/Bogota"))

                if (radgCalRecordar.checkedRadioButtonId != -1) {
                    when (radgCalRecordar.checkedRadioButtonId) {
                        radbUnaSemana.id -> realizarEventoCalendario(
                            dateTime.dayOfMonth + 7,
                            dateTime.dayOfMonth,
                            dateTime.year
                        )
                        radbQuinceDias.id -> realizarEventoCalendario(
                            dateTime.dayOfMonth + 15,
                            dateTime.dayOfMonth,
                            dateTime.year
                        )
                        radbUnMes.id -> realizarEventoCalendario(
                            dateTime.dayOfMonth + 30,
                            dateTime.dayOfMonth,
                            dateTime.year
                        )
                    }
                    dialog.cancel()
                } else {
                    AppMessage.enviarMensaje(
                        view, "INFO: Debe seleccionar un tiempo para que sea registrado",
                        TypeMessage.INFO
                    )
                    dialog.cancel()
                }
            }
            .show()
    }

    private fun realizarEventoCalendario(dia: Int, mes: Int, anio: Int) {
        val cal = Calendar.getInstance()

        cal.set(Calendar.DAY_OF_MONTH, dia)
        cal.set(Calendar.MONTH, mes)
        cal.set(Calendar.YEAR, anio)

        cal.set(Calendar.HOUR_OF_DAY, 12)
        cal.set(Calendar.MINUTE, 30)

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.timeInMillis)
        intent.putExtra(
            CalendarContract.EXTRA_EVENT_END_TIME, cal.timeInMillis + 60 * 60 * 1000
        ) //1h

        intent.putExtra(CalendarContract.Events.ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY")
        intent.putExtra(CalendarContract.Events.TITLE, "Pedir ${medicamento.nombre_producto}")
        intent.putExtra(
            CalendarContract.Events.DESCRIPTION,
            "La última vez pediste ${medicamento.pedido} UN\nEl precio total fue de s/. ${medicamento.precio_total}"
        )

        startActivity(context, intent, null)
    }

}