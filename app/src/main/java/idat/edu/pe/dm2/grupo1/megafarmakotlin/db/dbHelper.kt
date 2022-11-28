package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbHelper(context: Context) : SQLiteOpenHelper(
    context, "db.reclamos", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        var ordenCreacion = "CREATE TABLE reclamos" +
                "(idReclamo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "motivo TEXT," +
                "descripcion TEXT)"
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS reclamos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun aniadorDatos(motivo: String, descripcion: String){
        val datos = ContentValues()
        datos.put("motivo", motivo)
        datos.put("descripcion",descripcion)

        val db = this.writableDatabase
        db.insert("reclamos", null, datos)
        db.close()
    }
}