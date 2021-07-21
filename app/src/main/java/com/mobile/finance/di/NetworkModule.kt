package com.mobile.finance.di

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.mobile.finance.network.FinanceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://10.0.2.2:8080/"

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

  @Singleton
  @Provides
  fun provideRetrofitService(): FinanceApiService {
    Log.i("myLogTag", "Creating api service")
    val om = ObjectMapper()
    om.registerModule(JavaTimeModule())
    return Retrofit.Builder()
      .addConverterFactory(JacksonConverterFactory.create(om))
      .baseUrl(BASE_URL)
      .build()
      .create(FinanceApiService::class.java)
  }
}