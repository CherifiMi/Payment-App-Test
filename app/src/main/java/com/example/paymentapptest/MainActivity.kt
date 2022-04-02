package com.example.paymentapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.coroutineScope
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.view.CardInputWidget
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainActivity : AppCompatActivity() {

    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration

    lateinit var paymentIntentClientSecret: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycle.coroutineScope.launch {
            startCheckout()
        }

        var card: CardInputWidget = findViewById(R.id.cardInputWidget)
        var btn: Button = findViewById(R.id.buttonPanel)
    }
    private suspend fun startCheckout(){
       //var hi = RetrofitInstance.api.getHello()
       // Log.d("RETROTEST", hi.string())

        var requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("amount","1222")
            .addFormDataPart("currency","eur")
            .build()

        paymentIntentClientSecret = RetrofitInstance.api.PaymentIntent(requestBody).string()

        Log.d("RETROTEST", paymentIntentClientSecret)
    }

}