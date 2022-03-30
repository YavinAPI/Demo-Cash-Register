package com.yavin.cashregister.network

import android.annotation.SuppressLint
import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    fun buildUrl(hostIp:String="", port:String="16125", path: String): String {
        //GET http://<IP_LOCALE>:16125/localapi/v1/payment/<MONTANT>/
        return "http://${hostIp}:${port}.localapi/v1/$path"
    }

    @Singleton
    @Provides
    fun providesRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
            //.baseUrl(buildUrl(""))
            .baseUrl("http://yavin.com/")
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun getGSONConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
                GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .setPrettyPrinting()
                        .create()
        )
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
            builder: OkHttpClient.Builder,
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesUnsafeOkHttpClientBuilder(): OkHttpClient.Builder {
        val truestCert = getNewTrustCert()

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, truestCert, java.security.SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.connectTimeout(1, TimeUnit.MINUTES)
        okHttpBuilder.readTimeout(1, TimeUnit.MINUTES)
        okHttpBuilder.writeTimeout(1, TimeUnit.MINUTES)
        okHttpBuilder.retryOnConnectionFailure(true)

        okHttpBuilder.cache(null)

        if (truestCert.isNotEmpty()) {
            okHttpBuilder.sslSocketFactory(sslSocketFactory, truestCert[0] as X509TrustManager)
        }

        okHttpBuilder.hostnameVerifier { _, _ -> true }
        okHttpBuilder.retryOnConnectionFailure(true)

        return okHttpBuilder
    }

    private fun getNewTrustCert() = arrayOf<TrustManager>(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })
}