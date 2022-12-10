package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import androidx.lifecycle.LiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao.AuthDAO
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity

class AuthSQLiteRepository(private val authDAO: AuthDAO) {
    suspend fun insertar(authEntity: AuthEntity) {
        authDAO.insertar(authEntity)
    }

    suspend fun actualizar(authEntity: AuthEntity) {
        authDAO.actualizar(authEntity)
    }

    suspend fun eliminarTodo() {
        authDAO.eliminarTodo()
    }

    fun obtener(): LiveData<AuthEntity> {
        return authDAO.obtener()
    }
}