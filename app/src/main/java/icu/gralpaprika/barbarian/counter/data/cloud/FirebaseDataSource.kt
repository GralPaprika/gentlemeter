package icu.gralpaprika.barbarian.counter.data.cloud

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import icu.gralpaprika.barbarian.counter.data.cloud.model.ActDocument
import icu.gralpaprika.barbarian.counter.data.cloud.model.ApologyDocument
import icu.gralpaprika.barbarian.counter.data.cloud.model.LevelDocument
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class FirebaseDataSource @Inject constructor(
    firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    private val userId = firebaseAuth.currentUser?.uid
        ?: throw IllegalStateException(AUTH_ERROR)
    private val db = firestore.collection(USER_COLLECTION)

    suspend fun setBarbarianLevel(level: LevelDocument) {
        db.document(userId)
            .collection(BARBARIAN_LEVEL_COLLECTION)
            .document(DEFAULT_LEVEL_DOCUMENT_ID)
            .set(level)
            .await()
    }


    suspend fun getBarbarianLevel(): LevelDocument? =
        db.document(userId)
            .collection(BARBARIAN_LEVEL_COLLECTION)
            .document(DEFAULT_LEVEL_DOCUMENT_ID)
            .get()
            .await()
            .run { toObject(LevelDocument::class.java) }

    suspend fun saveAct(act: ActDocument) {
        db.document(userId)
            .collection(BARBARIAN_ACTS_COLLECTION)
            .document(act.id)
            .set(act)
            .await()
    }

    suspend fun getAllActs(): List<ActDocument> =
        db.document(userId)
            .collection(BARBARIAN_ACTS_COLLECTION)
            .get()
            .await()
            .run { documents.mapNotNull { it.toObject(ActDocument::class.java) } }

    suspend fun getAllApologies(): List<ApologyDocument> =
        db.document(userId)
            .collection(APOLOGIES_COLLECTION)
            .get()
            .await()
            .run { documents.mapNotNull { it.toObject(ApologyDocument::class.java) } }

    companion object {
        private const val USER_COLLECTION = "user"
        private const val BARBARIAN_LEVEL_COLLECTION = "level"
        private const val BARBARIAN_ACTS_COLLECTION = "acts"
        private const val APOLOGIES_COLLECTION = "apologies"
        private const val DEFAULT_LEVEL_DOCUMENT_ID = "0" // Document references must have an even number of segments
        private const val AUTH_ERROR = "User must be authenticated"
    }
}
