package com.nishant.compose.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WorldTimeApi {

    @GET("/api/timezone/{area}/{city}")
    suspend fun getTime(
        @Path("area") area: String,
        @Path("city") city: String
    ): Call<WorldTimeResponse>
}