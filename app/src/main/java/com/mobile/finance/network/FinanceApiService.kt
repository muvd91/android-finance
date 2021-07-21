package com.mobile.finance.network

import retrofit2.http.Body
import retrofit2.http.POST

interface FinanceApiService {

  @POST("entries")
  suspend fun postEntry(@Body entry: EntryRequestBody): Unit
}