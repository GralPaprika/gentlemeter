package icu.gralpaprika.barbarian.counter.data.cloud

import com.google.firebase.firestore.FirebaseFirestore
import icu.gralpaprika.barbarian.counter.data.cloud.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.data.cloud.model.BarbarianLevel
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class FirebaseDataSource @Inject constructor(
    firestore: FirebaseFirestore
) {
    private val barbarianLevelCollection = firestore.collection(BARBARIAN_LEVEL_COLLECTION)
    private val barbarianActsCollection = firestore.collection(BARBARIAN_ACTS_COLLECTION)
    private val apologiesCollection = firestore.collection(APOLOGIES_COLLECTION)

    suspend fun setBarbarianLevel(level: BarbarianLevel) {
        barbarianLevelCollection.document(level.id).set(level).await()
    }

    suspend fun getBarbarianLevel(id: String): BarbarianLevel? {
        val snapshot = barbarianLevelCollection.document(id).get().await()
        return snapshot.toObject(BarbarianLevel::class.java)
    }

    suspend fun getAllLevels(): List<BarbarianLevel> {
        val snapshot = barbarianLevelCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(BarbarianLevel::class.java) }
    }

    suspend fun getAllActs(): List<BarbarianAct> {
        val snapshot = barbarianActsCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(BarbarianAct::class.java) }
    }

    suspend fun getAllApologies(): List<icu.gralpaprika.barbarian.counter.data.cloud.model.Apology> {
        val snapshot = apologiesCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(icu.gralpaprika.barbarian.counter.data.cloud.model.Apology::class.java) }
    }

    companion object {
        private const val BARBARIAN_LEVEL_COLLECTION = "barbarian_level"
        private const val BARBARIAN_ACTS_COLLECTION = "barbarian_acts"
        private const val APOLOGIES_COLLECTION = "apologies"
    }
}
