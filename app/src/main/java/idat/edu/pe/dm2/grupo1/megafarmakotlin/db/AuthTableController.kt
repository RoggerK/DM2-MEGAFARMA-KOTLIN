/*
package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.ContentValues
import android.content.Context
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.model.AuthTable

class AuthTableController(context: Context) : MegaFarmaRoomDatabase(context) {

    fun createAuth(auth: AuthTable): Boolean {
        val values = ContentValues()

        values.put("token", auth.token)
        values.put("nombre", auth.nombre)
        values.put("apellido", auth.apellido)
        values.put("dni", auth.dni)
        values.put("correo", auth.correo)
        values.put("idcliente", auth.idcliente)

        //usar de tipo insert - de tipo escritura
        val db = this.writableDatabase

        //sole devolvera un boolean si en caso si la confirmacion es mayor a 0, sino retorna -1
        val createSuccessful: Boolean = db.insert("auth", null, values) > 0
        db.close()

        return createSuccessful
    }

    fun getAuth(): AuthTable {
        val sql = "SELECT id, token, nombre, apellido, dni, correo, idcliente FROM auth WHERE id = 1"
        var authTable = AuthTable(0,"","","","","",0)

        //usamos formato lectura
        val db = this.readableDatabase
        val cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            val id = cursor.getString(0).toInt()
            val token = cursor.getString(1).toString()
            val nombre = cursor.getString(2).toString()
            val apellido = cursor.getString(3).toString()
            val dni = cursor.getString(4).toString()
            val correo = cursor.getString(5).toString()
            val idcliente = cursor.getString(6).toInt()

            authTable = AuthTable(id, token, nombre, apellido, dni, correo, idcliente)
        }

        cursor.close()
        db.close()

        return authTable
    }

    */
/*fun update(ContactoEntity contactoEntity): Boolean {
        val values = ContentValues()

        values.put("nombre", contactoEntity.getNombre())
        values.put("email", contactoEntity.getEmail())

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(contactoEntity.getId()) };

        db = this.getWritableDatabase()

        val updateSuccessful: Boolean = db.update("contactos", values, where, whereArgs) > 0
        db.close()

        return updateSuccessful
    }*//*


    */
/*fun delete(id: Int): Boolean {
        db = this.getWritableDatabase()
        val deleteSuccessful: Boolean = db.delete("contactos", "id = '" + id + "'", null) > 0
        db.close()

        return deleteSuccessful
    }*//*

}
*/
