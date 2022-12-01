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

        db!!.execSQL(ordenCreacion)

        aniadirDatos("¿Cómo comprar?","Usted puede añadir productos y ver el subtotal en el carrito")
        aniadirDatos("¿Nuestros productos son originales?","Sí, nuestro productos son 100% originales" +
                "no vendemos productos genericos")
        aniadirDatos("¿Tienen libro de reclamaciones?","Así es, contamos con un libro de reclamaciones" +
                "al cual pueden acceder de manera online a travéz del aplicativo")
        aniadirDatos("¿Puedo comprar varias veces en un día?","Sí, usted puede realizar las compras que desee" +
                "durante el día siempre y cuando haya un stock disponible")

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