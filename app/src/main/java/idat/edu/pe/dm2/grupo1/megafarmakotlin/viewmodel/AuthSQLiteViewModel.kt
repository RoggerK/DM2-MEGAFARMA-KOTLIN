package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.MegaFarmaRoomDatabase
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.AuthSQLiteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthSQLiteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthSQLiteRepository

    init {
        val authDAO = MegaFarmaRoomDatabase.getDatabase(application).authDAO()
        repository = AuthSQLiteRepository(authDAO)
    }

    fun insertar(authEntity: AuthEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertar(authEntity)
        }

    fun actualizar(authEntity: AuthEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizar(authEntity)
        }

    fun eliminarTodo() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarTodo()
        }

    fun obtener(): LiveData<AuthEntity> {
        return repository.obtener()
    }
}