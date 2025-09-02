package icu.gralpaprika.barbarian.counter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.data.mapper.BarbarianLevelToCloudBarbarianLevelMapper
import icu.gralpaprika.barbarian.counter.data.cloud.model.BarbarianLevel as CloudBarbarianLevel
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Provides
    @Singleton
    fun provideBarbarianLevelToCloudBarbarianLevelMapper(): Mapper<BarbarianLevel, CloudBarbarianLevel> {
        return BarbarianLevelToCloudBarbarianLevelMapper()
    }


}