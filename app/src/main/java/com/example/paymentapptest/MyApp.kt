package com.example.paymentapptest

import android.app.Application
import com.stripe.android.PaymentConfiguration

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51Kjbz4AuBgxbOOjRUEVzKJ0ORYH3dsGLecgsoDPsbwuLxacZk6Fog5DUSMwPYwvMstFnPZJCp02Ni7NLY7EF6KF700bZUbd2MN"
        )
    }
}