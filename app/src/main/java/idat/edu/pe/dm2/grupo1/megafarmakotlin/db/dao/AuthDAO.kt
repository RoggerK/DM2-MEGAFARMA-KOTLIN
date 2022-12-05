package idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity

@Dao
interface AuthDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertar(vararg auth: AuthEntity)

    @Update
    fun actualizar(vararg auth: AuthEntity)

    @Query("DELETE FROM auth")
    fun eliminarTodo()

    @Query("SELECT * FROM auth LIMIT 1")
    fun obtener(): LiveData<AuthEntity>
}