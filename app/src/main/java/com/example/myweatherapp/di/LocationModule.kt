package com.example.myweatherapp.di

import com.example.myweatherapp.repositories.LocationRepository
import com.example.myweatherapp.repositories.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by PremtimFarizi on 22/Mar/2025
 **/
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        repositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}