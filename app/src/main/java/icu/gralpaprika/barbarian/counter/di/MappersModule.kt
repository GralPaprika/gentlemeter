package icu.gralpaprika.barbarian.counter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import icu.gralpaprika.barbarian.counter.data.cloud.model.ApologyDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.data.cloud.model.ActDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.data.mapper.ApologyToApologyDocumentMapper
import icu.gralpaprika.barbarian.counter.data.mapper.BarbarianActToActDocumentMapper
import icu.gralpaprika.barbarian.counter.data.mapper.BarbarianLevelToLevelDocumentMapper
import icu.gralpaprika.barbarian.counter.data.cloud.model.LevelDocument
import icu.gralpaprika.barbarian.counter.data.mapper.ActDocumentToBarbarianActMapper
import icu.gralpaprika.barbarian.counter.data.mapper.LevelDocumentToBarbarianLevelMapper
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Provides
    @Singleton
    fun provideBarbarianLevelToLevelDocumentMapper(): Mapper<BarbarianLevel, LevelDocument> =
        BarbarianLevelToLevelDocumentMapper()

    @Provides
    @Singleton
    fun provideBarbarianActToActDocumentMapper(): Mapper<BarbarianAct, ActDocument> =
        BarbarianActToActDocumentMapper()

    @Provides
    @Singleton
    fun provideApologyToApologyDocumentMapper(): Mapper<Apology, ApologyDocument> =
        ApologyToApologyDocumentMapper()

    @Provides
    @Singleton
    fun provideLevelDocumentToBarbarianLevelMapper(): Mapper<LevelDocument, BarbarianLevel> =
        LevelDocumentToBarbarianLevelMapper()

    @Provides
    @Singleton
    fun provideActDocumentToBarbarianActMapper(): Mapper<ActDocument, BarbarianAct> =
        ActDocumentToBarbarianActMapper()
}