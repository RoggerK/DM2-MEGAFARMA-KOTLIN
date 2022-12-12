package idat.edu.pe.dm2.grupo1.megafarmakotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao.AuthDAO
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity

@Database(entities = [AuthEntity::class], version = 1)
abstract class MegaFarmaRoomDatabase : RoomDatabase() {

    abstract fun authDAO(): AuthDAO

    //Todo lo que tenga este bloque serán definidos como static
    companion object {
        @Volatile
        private var INSTANCE: MegaFarmaRoomDatabase? = null

        fun getDatabase(context: Context): MegaFarmaRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
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

}




