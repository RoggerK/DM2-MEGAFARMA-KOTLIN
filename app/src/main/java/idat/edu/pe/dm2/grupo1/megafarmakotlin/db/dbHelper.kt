package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class dbHelper(context: Context) : SQLiteOpenHelper(
    context, "db.megafarma", null, 1
) {

    override fun onCreate(db: SQLiteDatabase) {
        val authCreacion = "CREATE TABLE auth(" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "idcliente INTEGER," +
                                "nombre TEXT," +
                                "apellido TEXT," +
                                "dni TEXT," +
                                "correo TEXT," +
                                "token TEXT" +
                            ")"

        val preguntasCreacion = "CREATE TABLE preguntas(" +
                                    "idPreguntas INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "titulo TEXT," +
                                    "descripcion TEXT" +
                                ")"

        db.execSQL(authCreacion)
        db.execSQL(preguntasCreacion)

        val dato1 = ContentValues()
        val dato2 = ContentValues()
        val dato3 = ContentValues()
        val dato4 = ContentValues()

        dato1.put("titulo", "¿Cómo comprar?")
        dato1.put("descripcion", "Usted puede añadir productos y ver el subtotal en el carrito")

        dato2.put("titulo", "¿Nuestros productos son originales?")
        dato2.put("descripcion", "Sí, nuestro productos son 100% originales no vendemos productos genericos")

        dato3.put("titulo", "¿Tienen libro de reclamaciones?")
        dato3.put("descripcion", "Así es, contamos con un libro de reclamaciones al cual pueden acceder de manera online a travéz del aplicativo")

        dato4.put("titulo", "¿Puedo comprar varias veces en un día?")
        dato4.put("descripcion", "Sí, usted puede realizar las compras que desee durante el día siempre y cuando haya un stock disponible")

        db.insert("preguntas", null, dato1)
        db.insert("preguntas", null, dato2)
        db.insert("preguntas", null, dato3)
        db.insert("preguntas", null, dato4)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS reclamos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }



}




