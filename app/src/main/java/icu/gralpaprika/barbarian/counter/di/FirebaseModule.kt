package icu.gralpaprika.barbarian.counter.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import icu.gralpaprika.barbarian.counter.data.cloud.FirebaseDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDataSource(firestore: FirebaseFirestore): FirebaseDataSource = FirebaseDataSource(firestore)
}

