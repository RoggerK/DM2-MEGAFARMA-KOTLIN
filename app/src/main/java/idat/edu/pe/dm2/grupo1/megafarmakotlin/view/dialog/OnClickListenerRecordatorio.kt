package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse

class OnClickListenerRecordatorio(var medicamento: MedicamentoResponse) : View.OnClickListener {

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
                if (radgCalRecordar.checkedRadioButtonId != -1) {
                    when(radgCalRecordar.checkedRadioButtonId) {
                        radbUnaSemana.id -> Toast.makeText(context, "Semana", Toast.LENGTH_SHORT).show()
                        radbQuinceDias.id -> Toast.makeText(context, "Quince", Toast.LENGTH_SHORT).show()
                        radbUnMes.id -> Toast.makeText(context, "Mes", Toast.LENGTH_SHORT).show()
                    }
                    dialog.cancel()
                } else {
                    AppMessage.enviarMensaje(view, "INFO: Debe seleccionar un tiempo para que sea registrado",
                        TypeMessage.INFO)
                }
            }
            .show()
    }

    /*@Override
                    fun void onClick(DialogInterface dialog, int which) {
                        String contactoNombre = etNombre . getText ().toString();
                        String contactoEmail = etEmail . getText ().toString();

                        ContactoEntity contactoEntity = new ContactoEntity();
                        contactoEntity.setNombre(contactoNombre);
                        contactoEntity.setEmail(contactoEmail);

                        boolean createSuccessful = new ContactoTableController(context).create(
                            contactoEntity
                        );
                        if (createSuccessful) {
                            Toast.makeText(
                                context,
                                "Informacion de contacto guardada",
                                Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Toast.makeText(
                                context,
                                "No se pudo guardar la informacion en contacto",
                                Toast.LENGTH_SHORT
                            ).show();
                        }

                        ((MainActivity) context).readRecords();
                        ((MainActivity) context).countRecords();

                        dialog.cancel();
                    }
                }).show();*/
}