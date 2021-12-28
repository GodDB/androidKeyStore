package com.nonamed1.testkeystore

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@RequiresApi(Build.VERSION_CODES.M)
object Encryptor {
    private const val ALGORISM = "AES/GCM/NoPadding"
    private const val KEYSTORE_NAME = "AndroidKeyStore"


    operator fun invoke(alias: String, value: String): EncryptData {
        val cipher = Cipher.getInstance(ALGORISM).apply {
            init(Cipher.ENCRYPT_MODE, getSecretKey(alias = alias))
        }
        return EncryptData(
            encryptData = cipher.doFinal(value.toByteArray()),
            iv = cipher.iv
        )
    }

    private fun getSecretKey(alias: String): SecretKey {
        val keyGenSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM) //필수
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        //aes는 하나의 secret key이므로 keyGenerator를, rsa는 하나의 공개키와 비밀키를 가짐으로 keypairGenerator를 사용한다.
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_NAME).apply {
            init(keyGenSpec)
        }.run {
            generateKey()
        }
    }
}

data class EncryptData(val encryptData: ByteArray, val iv: ByteArray)