package com.apptikar.csvreadertask.di

import com.apptikar.csvreadertask.domain.usecases.CsvFileParserUseCase
import com.apptikar.csvreadertask.domain.usecases.RecordsSorterUseCase
import com.apptikar.csvreadertask.domain.usecases.XlsFileParserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideRepository() : Repository = RepositoryImp()

    @Provides
    @Singleton
    fun provideCsvParserUseCase() : CsvFileParserUseCase = CsvFileParserUseCase()

    @Provides
    @Singleton
    fun provideXlsParserUseCase() : XlsFileParserUseCase = XlsFileParserUseCase()

    @Provides
    @Singleton
    fun provideRecordsSorter() : RecordsSorterUseCase = RecordsSorterUseCase()

}