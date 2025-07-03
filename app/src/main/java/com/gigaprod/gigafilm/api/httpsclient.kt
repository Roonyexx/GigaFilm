package com.gigaprod.gigafilm.api

import android.content.Context
import com.gigaprod.gigafilm.R
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun getSafeOkHttpClient(context: Context): OkHttpClient {
    val cf = CertificateFactory.getInstance("X.509")
    val caInput = context.resources.openRawResource(R.raw.server)
    val ca = caInput.use { cf.generateCertificate(it) }

    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
        load(null, null)
        setCertificateEntry("ca", ca)
    }

    val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
        init(keyStore)
    }

    val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, tmf.trustManagers, null)
    }

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
        .build()
}
