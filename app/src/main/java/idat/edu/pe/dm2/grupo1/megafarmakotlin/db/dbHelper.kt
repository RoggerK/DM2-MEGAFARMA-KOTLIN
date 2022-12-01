package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbHelper(context: Context) : SQLiteOpenHelper(
    context, "db.megafarma", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE preguntas" +
                "(idPreguntas INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "descripcion TEXT)"
        val insertDatos = "INSERT INTO preguntas(titulo, descripcion)" +
                "VALUES('¿Puedo comprar varias veces en un día?','Sí, usted puede realizar las compras que desee')"

        db!!.execSQL(ordenCreacion)
        db!!.execSQL(insertDatos)

    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS reclamos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }


}