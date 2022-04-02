package com.example.paymentapptest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://stripetest15.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: PaymentAPI by lazy {
        retrofit.create(PaymentAPI::class.java)
    }
}