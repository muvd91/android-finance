package com.mobile.finance.di

import android.content.Context
import android.util.Log
import com.mobile.finance.data.FinanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): FinanceDatabase {
    Log.i("myLogTag", "Creating database")
    return FinanceDatabase.newInstance(context)
  }

}