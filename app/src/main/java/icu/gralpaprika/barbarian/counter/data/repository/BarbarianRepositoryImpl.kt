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

    private suspend fun getLatestLevel(): BarbarianLevel {
        return levelDao.getBarbarianLevel().firstOrNull() ?:
            BarbarianLevel(level = minBarbarianLevel)
    }

    override suspend fun increaseBarbarianLevel() {
        try {
            saveAct(BarbarianAct(type = ActsType.Barbarian.value))
            getLatestLevel().let {
                updateLevel(it.copy(level = it.level + 1, synced = false))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error increasing barbarian level", e)
        }
    }

    override suspend fun decreaseBarbarianLevel() {
        try {
            saveAct(BarbarianAct(type = ActsType.Gentleman.value))
            getLatestLevel().let {
                updateLevel(it.copy(
                    level = (it.level - 1).coerceAtLeast(minBarbarianLevel),
                    synced = false
                ))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error decreasing barbarian level", e)
        }
    }

    override suspend fun increaseAndResetBarbarianLevel() {
        try {
            saveAct(BarbarianAct(type = ActsType.Barbarian.value))
            updateLevel(getLatestLevel().copy(level = minBarbarianLevel, synced = false))
        } catch (e: Exception) {
            Log.e(TAG, "Error resetting barbarian level", e)
        }
    }

    override suspend fun getCurrentBarbarianLevel(): Int {
        return try {
            levelDao.getBarbarianLevel().firstOrNull()?.level ?: run {
                levelDao.updateBarbarianLevel(BarbarianLevel(level = minBarbarianLevel))
                minBarbarianLevel
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting barbarian level", e)
            minBarbarianLevel
        }
    }

    override suspend fun sync() {
        try {
            firebaseDataSource.getBarbarianLevel()?.let {
                toBarbarianLevelMapper.map(it).let { level ->
                    levelDao.updateBarbarianLevel(level)
                }
            } ?: run {
                levelDao.getBarbarianLevel().firstOrNull()?.let {
                    firebaseDataSource.setBarbarianLevel(toLevelDocumentMapper.map(it))
                    levelDao.updateBarbarianLevel(it.copy(synced = true))
                }
            }

            firebaseDataSource.getAllActs().forEach {
                actDao.insert(toBarbarianActMapper.map(it))
            }

            actDao.getAllNotSynced().forEach {
                firebaseDataSource.saveAct(toActDocumentMapper.map(it))
            }
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Error syncing barbarian level: ${e.message}", e)
        }
    }

    private suspend fun saveAct(act: BarbarianAct) {
        actDao.insert(act)
        try {
            firebaseDataSource.saveAct(toActDocumentMapper.map(act))
            actDao.markAsSynced(act.id)
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Error saving on the cloud: ${e.message}", e)
        }
    }

    private suspend fun updateLevel(level: BarbarianLevel) {
        levelDao.updateBarbarianLevel(level)
        try {
            firebaseDataSource.setBarbarianLevel(toLevelDocumentMapper.map(level))
            levelDao.updateBarbarianLevel(level.copy(synced = true))
        } catch (e: IllegalStateException) {
            Log.e(TAG, "Error saving level on the cloud: ${e.message}", e)
        }
    }

    companion object {
        private const val TAG = "BarbarianRepository"
    }
}
