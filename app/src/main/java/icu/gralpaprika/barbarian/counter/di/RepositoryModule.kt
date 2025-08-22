package icu.gralpaprika.barbarian.counter.di

import icu.gralpaprika.barbarian.counter.data.repository.BarbarianRepositoryImpl
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindBarbarianRepository(
        barbarianRepositoryImpl: BarbarianRepositoryImpl
    ): BarbarianRepository
}

