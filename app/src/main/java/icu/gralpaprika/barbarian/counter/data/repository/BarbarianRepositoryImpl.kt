package icu.gralpaprika.barbarian.counter.data.repository

import android.util.Log
import icu.gralpaprika.barbarian.counter.data.cloud.FirebaseDataSource
import icu.gralpaprika.barbarian.counter.data.cloud.model.ActDocument
import icu.gralpaprika.barbarian.counter.data.cloud.model.LevelDocument
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianActDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianLevelDao
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import icu.gralpaprika.barbarian.counter.domain.model.ActsType
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import icu.gralpaprika.barbarian.counter.BuildConfig
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarbarianRepositoryImpl @Inject constructor(
    private val actDao: BarbarianActDao,
    private val levelDao: BarbarianLevelDao,
    private val firebaseDataSource: FirebaseDataSource,
    private val toActDocumentMapper: Mapper<BarbarianAct, ActDocument>,
    private val toLevelDocumentMapper: Mapper<BarbarianLevel, LevelDocument>,
    private val toBarbarianActMapper: Mapper<ActDocument, BarbarianAct>,
    private val toBarbarianLevelMapper: Mapper<LevelDocument, BarbarianLevel>,
) : BarbarianRepository {
    private val minBarbarianLevel = BuildConfig.BARBARIAN_MIN_LEVEL

    override suspend fun getLatestRemoteUpdateDate(): Long? =
        firebaseDataSource.getBarbarianLevel()?.lastUpdated

    override suspend fun getLatestLocalUpdateDate(): Long =
        getLatestLevel().lastUpdated

    override suspend fun increaseBarbarianLevel() {
        changeBarbarianLevel(
            actType = ActsType.Barbarian,
            newLevel = getLatestLevel().level + 1,
            errorMsg = "Error increasing barbarian level",
        )
    }

    override suspend fun decreaseBarbarianLevel() {
        changeBarbarianLevel(
            actType = ActsType.Gentleman,
            newLevel = (getLatestLevel().level - 1).coerceAtLeast(minBarbarianLevel),
            errorMsg = "Error decreasing barbarian level",
        )
    }

    override suspend fun increaseAndResetBarbarianLevel() {
        changeBarbarianLevel(
            actType = ActsType.Barbarian,
            newLevel = minBarbarianLevel,
            errorMsg = "Error resetting barbarian level"
        )
    }

    override suspend fun getCurrentBarbarianLevel(): Int {
        return try {
            getLatestLevel().level
        } catch (e: Exception) {
            Log.e(TAG, "Error getting barbarian level", e)
            minBarbarianLevel
        }
    }

    override suspend fun syncToCloud(): SyncResult {
        try {
            // TODO: Optimize fetching performance.
            actDao.getAllNotSynced().forEach { act ->
                firebaseDataSource.saveAct(toActDocumentMapper.map(act))
                actDao.markAsSynced(act.id)
            }

            firebaseDataSource.setBarbarianLevel(toLevelDocumentMapper.map(getLatestLevel()))

            return SyncResult.Success
        } catch (e: Exception) {
            Log.e(TAG, SyncResult.ErrorType.SyncToCloudFailed.description, e)
            return SyncResult.Error(SyncResult.ErrorType.SyncToCloudFailed)
        }
    }

    override suspend fun syncFromCloud(): SyncResult {
        try {
            // TODO: Optimize fetching performance.
            firebaseDataSource.getAllActs().forEach {
                actDao.insert(toBarbarianActMapper.map(it))
            }

            firebaseDataSource.getBarbarianLevel()?.let {
                levelDao.updateBarbarianLevel(toBarbarianLevelMapper.map(it).copy(synced = true))
            }

            return SyncResult.Success
        } catch (e: Exception) {
            Log.e(TAG, SyncResult.ErrorType.SyncFromCloudFailed.description, e)
            return SyncResult.Error(SyncResult.ErrorType.SyncFromCloudFailed)
        }
    }

    private suspend fun getLatestLevel(): BarbarianLevel =
        levelDao.getBarbarianLevel() ?: BarbarianLevel(
            level = minBarbarianLevel,
            lastUpdated = DEFAULT_TIMESTAMP,
        )

    private suspend fun changeBarbarianLevel(
        actType: ActsType,
        newLevel: Int,
        errorMsg: String
    ) {
        try {
            actDao.insert(BarbarianAct(type = actType.value))
            levelDao.updateBarbarianLevel(getLatestLevel().copy(
                level = newLevel,
                synced = false,
                lastUpdated = System.currentTimeMillis()
            ))
        } catch (e: Exception) {
            Log.e(TAG, errorMsg, e)
        }
    }

    companion object {
        private const val TAG = "BarbarianRepository"
        private const val DEFAULT_TIMESTAMP = 0L
    }
}
