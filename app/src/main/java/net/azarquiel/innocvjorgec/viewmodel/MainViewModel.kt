package net.azarquiel.innocvjorgec.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.innocvjorgec.api.MainRepository
import net.azarquiel.innocvjorgec.model.User
import retrofit2.Call


/**
 * Created by JorgeC on 22/02/2020.
 */

class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getDataUsers(): LiveData<List<User>> {
        val dataUsers = MutableLiveData<List<User>>()
        GlobalScope.launch(Main) {
            dataUsers.value = repository.getDataUsers()
        }
        return dataUsers
    }

   /* fun saveUser( user: User):LiveData<User> {
        val user2 = MutableLiveData<User>()

        GlobalScope.launch(Main) {
            user2.value = repository.saveUser(user)
        }
        return user2
    }*/

    fun saveUser( user: User):LiveData<User> {
        val user2 = MutableLiveData<User>()

        GlobalScope.launch(Main) {
           repository.saveUser(user)
        }
        return user2
    }

    fun deleteUser(id:Int):LiveData<User> {
        val user2 = MutableLiveData<User>()
        GlobalScope.launch(Main) {
            repository.deleteUser(id)
        }
        return user2
    }

    /*fun deleteUser(id:Int):LiveData<User> {
        val user2 = MutableLiveData<User>()
        GlobalScope.launch(Main) {
            user2.value = repository.deleteUser(id)
        }
        return user2
    }*/
}
