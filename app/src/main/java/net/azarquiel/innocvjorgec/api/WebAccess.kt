package net.azarquiel.innocvjorgec.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import net.azarquiel.innocv.api.MyManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by JorgeC on 22/02/2020.
 */

object WebAccess {

    val apiService : ApiService by lazy {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://hello-world.innocv.com/api/")
            .client(createOkHttpClient())
            .build()

        return@lazy retrofit.create(ApiService::class.java)
    }
    private fun createOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(MyManager())
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { hostname, session -> true }
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
