package icu.gralpaprika.barbarian.counter.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import icu.gralpaprika.barbarian.counter.data.database.BarbarianDatabase
import icu.gralpaprika.barbarian.counter.data.database.dao.ApologyDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianActDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianLevelDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBarbarianDatabase(@ApplicationContext context: Context): BarbarianDatabase =
        Room.databaseBuilder(
            context,
            BarbarianDatabase::class.java,
            "barbarian_database"
        ).build()

    @Provides
    fun provideBarbarianActDao(database: BarbarianDatabase): BarbarianActDao =
        database.barbarianActDao()

    @Provides
    fun provideBarbarianLevelDao(database: BarbarianDatabase): BarbarianLevelDao =
        database.barbarianLevelDao()

    @Provides
    fun provideApologyDao(database: BarbarianDatabase): ApologyDao =
        database.apologyDao()

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
