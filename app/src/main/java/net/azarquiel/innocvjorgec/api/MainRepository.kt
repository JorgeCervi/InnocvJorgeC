package net.azarquiel.innocvjorgec.api

import net.azarquiel.innocvjorgec.model.User
import org.jetbrains.anko.doAsync
import retrofit2.Call

/**
 * Created by JorgeC on 22/02/2020.
 */


class MainRepository() {
    val service = WebAccess.apiService

    suspend fun getDataUsers(): List<User> {
        val webResponse = service.getDataUsers().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return emptyList()
    }

    /*suspend fun saveUser(user:User
    ): User? {
        var user2: User? = null
        val webResponse = service.saveUser(user).await()
        if (webResponse.isSuccessful) {
            user2 = webResponse.body()!!.user
        }
        return user2
    }*/

    suspend fun saveUser(user:User
    ): Unit {
        val webResponse = service.saveUser(user).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return Unit

    }

    suspend fun deleteUser(id:Int
    ): Unit {
        val webResponse = service.deleteUser(id).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!
        }
        return Unit
    }

    /*suspend fun deleteUser(id:Int
    ): User? {
        var user2: User? = null
        val webResponse = service.deleteUser(id).await()
        if (webResponse.isSuccessful) {
            user2 = webResponse.body()!!.user
        }
        return user2
    }*/


}
