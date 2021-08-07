package com.nishant.compose.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nishant.compose.network.WorldTimeApi
import com.nishant.compose.network.WorldTimeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://worldtimeapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val api by lazy {
        retrofit.create(WorldTimeApi::class.java)
    }

    suspend fun getDelhiTime(liveData: MutableLiveData<IntArray>) {
        val response = api.getTime("Asia", "Kolkata")
        handleResponse(response) {
            liveData.value = it
        }
    }

    suspend fun getLondonTime(liveData: MutableLiveData<IntArray>) {
        val response = api.getTime("Europe", "London")
        handleResponse(response) {
            liveData.value = it
        }
    }

    suspend fun getTokyoTime(liveData: MutableLiveData<IntArray>) {
        val response = api.getTime("Asia", "Tokyo")
        handleResponse(response) {
            liveData.value = it
        }
    }

    suspend fun getNewYorkTime(liveData: MutableLiveData<IntArray>) {
        val response = api.getTime("America", "New_York")
        handleResponse(response) {
            liveData.value = it
        }
    }

    private fun handleResponse(
        response: Call<WorldTimeResponse>,
        callback: (IntArray) -> Unit
    ) {
        response.enqueue(object : Callback<WorldTimeResponse> {
            override fun onResponse(
                call: Call<WorldTimeResponse>,
                response: Response<WorldTimeResponse>
            ) {
                if (response.isSuccessful) {
                    val datetime = response.body()?.datetime ?: throw Exception("Body is null")
                    val time = datetime.substring(datetime.indexOf('T') + 1, datetime.indexOf('.'))
                    callback(time.split(":").map { it.toInt() }.toIntArray())
                }
            }

            override fun onFailure(call: Call<WorldTimeResponse>, t: Throwable) {
                Log.wtf("MainRepository", "onFailure", t)
            }
        })

//        if (response.isSuccessful) {
//            val datetime = response.body()?.datetime ?: throw Exception("Body is null")
//            val time = datetime.substring(datetime.indexOf('T') + 1, datetime.indexOf('.'))
//            return time.split(":").map { it.toInt() }.toIntArray()
//        }
//        throw Exception("Response unsuccessful")
    }
}