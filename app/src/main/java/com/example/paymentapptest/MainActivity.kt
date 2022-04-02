package com.example.paymentapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.view.CardInputWidget
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainActivity : AppCompatActivity() {

    lateinit var paymentIntentClientSecret: String
    private lateinit var paymentLauncher: PaymentLauncher

    lateinit var card: CardInputWidget
    lateinit var paybtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paymentConfiguration = PaymentConfiguration.getInstance(applicationContext)
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )




        card = findViewById(R.id.cardInputWidget)
        paybtn = findViewById(R.id.payButton)

        card.postalCodeEnabled = false


        startCheckout()
    }

    fun startCheckout() {

        lifecycle.coroutineScope.launch {

            var requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("amount", "20000")
                .addFormDataPart("currency", "usd")
                .build()

            paymentIntentClientSecret = RetrofitInstance.api.PaymentIntent(requestBody).string()

        }

        paybtn.setOnClickListener{
            Log.d("TESTPAY", "renning")
            card.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams
                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                lifecycleScope.launch {
                    paymentLauncher.confirm(confirmParams)
                }
            }
        }
    }
    private fun onPaymentResult(paymentResult: PaymentResult) {
        val message = when (paymentResult) {
            is PaymentResult.Completed -> {
                "Completed!"
            }
            is PaymentResult.Canceled -> {
                "Canceled!"
            }
            is PaymentResult.Failed -> {
                // This string comes from the PaymentIntent's error message.
                // See here: https://stripe.com/docs/api/payment_intents/object#payment_intent_object-last_payment_error-message
                "Failed: " + paymentResult.throwable.message
            }
        }
        Log.d("TESTPAY", message)
    }


}