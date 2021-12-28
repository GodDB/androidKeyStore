package com.nonamed1.testkeystore

import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object Decryptor {

    private const val ALGORISM = "AES/GCM/NoPadding"
    private const val KEYSTORE_NAME = "AndroidKeyStore"


    private val keyStore = KeyStore.getInstance(KEYSTORE_NAME).apply {
        load(null)
    }

    operator fun invoke(alias: String, encryptedData: EncryptData): String? {
        val notnullSecretKey = getSecretKey(alias) ?: return null
        val gcmParameterSpec = GCMParameterSpec(128, encryptedData.iv)
        return Cipher.getInstance(ALGORISM).apply {
            init(Cipher.DECRYPT_MODE, notnullSecretKey, gcmParameterSpec)
        }.run {
            String(doFinal(encryptedData.encryptData))
        }
    }

    private fun getSecretKey(alias: String): SecretKey? {
        return (keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.secretKey
    }
}