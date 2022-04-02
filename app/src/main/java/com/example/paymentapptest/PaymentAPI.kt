package com.example.paymentapptest

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentAPI {
    @GET("/")
    suspend fun getHello(): ResponseBody

    @POST("/stripe")
    suspend fun PaymentIntent(@Body requestBody: RequestBody): ResponseBody


}