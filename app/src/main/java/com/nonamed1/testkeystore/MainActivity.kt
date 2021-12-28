package com.nonamed1.testkeystore

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyInfo
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.annotation.RequiresApi
import java.security.KeyPair
import java.security.KeyPairGenerator

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = "godgod"
        val alias = "1"
        val encriptValue = Encryptor.invoke(alias = alias, value = text)
        Log.e("godgod", "$encriptValue")
        val value = Decryptor(alias = alias, encriptValue)
        Log.e("godgod", "$value")

    }
}