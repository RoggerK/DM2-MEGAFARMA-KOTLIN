/*
package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AuthTableController(context: Context) {
    private lateinit var db: SQLiteDatabase


    fun create(ContactoEntity contactoEntity): Boolean {
        val values = ContentValues()

        values.put("nombre", contactoEntity.getNombre())
        values.put("email", contactoEntity.getEmail())

        //usar de tipo insert - de tipo escritura
        db = this.getWritableDatabase()

        //sole devolvera un boolean si en caso si la confirmacion es mayor a 0, sino retorna -1
        val createSuccessful: Boolean = db.insert ("contactos", null, values) > 0
        db.close()

        return createSuccessful
    }

    fun update(ContactoEntity contactoEntity): Boolean {
        val values = ContentValues()

        values.put("nombre", contactoEntity.getNombre())
        values.put("email", contactoEntity.getEmail())

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(contactoEntity.getId()) };

        db = this.getWritableDatabase()

        val updateSuccessful: Boolean = db.update("contactos", values, where, whereArgs) > 0
        db.close()

        return updateSuccessful
    }

    fun delete(id: Int): Boolean {
        db = this.getWritableDatabase()
        val deleteSuccessful: Boolean = db.delete("contactos", "id = '" + id + "'", null) > 0
        db.close()

        return deleteSuccessful
    }
}*/
