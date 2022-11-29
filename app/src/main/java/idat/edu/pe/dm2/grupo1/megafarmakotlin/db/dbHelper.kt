package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbHelper(context: Context) : SQLiteOpenHelper(
    context, "db.preguntas", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE preguntas" +
                "(idPreguntas INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descripcion TEXT)"

        db!!.execSQL(ordenCreacion)

    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS reclamos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun aniadirDatos(titulo: String, descripcion: String){
        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("descripcion",descripcion)

        val db = this.writableDatabase
        db.insert("preguntas",null, datos)
        db.close()
    }

}