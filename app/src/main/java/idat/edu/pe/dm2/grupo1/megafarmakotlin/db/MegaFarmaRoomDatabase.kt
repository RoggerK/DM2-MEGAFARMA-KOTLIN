package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao.AuthDAO
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao.PreguntaDAO
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.PreguntaEntity

@Database(entities = [AuthEntity::class, PreguntaEntity::class], version = 1)
abstract class MegaFarmaRoomDatabase : RoomDatabase() { //(context: Context) : SQLiteOpenHelper(context, "db.megafarma", null, 1) {

    abstract fun authDAO(): AuthDAO
    abstract fun preguntaDAO(): PreguntaDAO
    //Todo lo que tenga este bloque serán definidos como static
    companion object{
        @Volatile
        private var INSTANCE: MegaFarmaRoomDatabase? = null

        fun getDatabase(context: Context) : MegaFarmaRoomDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MegaFarmaRoomDatabase::class.java,
                    "megafarmadb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    /*override fun onCreate(db: SQLiteDatabase) {
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
    }*/
}




