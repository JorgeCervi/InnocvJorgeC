package net.azarquiel.innocvjorgec.api
import kotlinx.coroutines.Deferred
import net.azarquiel.innocvjorgec.model.Respuesta
import net.azarquiel.innocvjorgec.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by JorgeC on 22/02/2020.
 */

interface ApiService {
    // Coger un usuario por id
    @GET("user/{id}")
    fun getDataUser(@Path("id") id: Int): Deferred<Response<Respuesta>>

    // Coger todos los usuarios
    @GET("user")
    fun getDataUsers(): Deferred<Response<List<User>>>


    // post con objeto en json
    @POST("user")
    //fun saveUser(@Body user: User): Deferred<Response<Respuesta>>
    fun saveUser(@Body user: User): Deferred<Response<Unit>>

    /*@POST("user")
    fun saveUser(@Body user: User): Call<Void>*/

    @DELETE("user/{id}")
    //fun deleteUser(@Path("id") id: Int): Deferred<Response<Respuesta>>
    fun deleteUser(@Path("id") id: Int): Deferred<Response<Unit>>
}