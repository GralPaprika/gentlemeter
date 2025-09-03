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
    override val maxBarbarianLevel = 10

    override val minBarbarianLevel = 0

    private var level: BarbarianLevel? = null

    override suspend fun increaseBarbarianLevel() {
        try {
            actDao.insert(BarbarianAct(type = ActsType.Barbarian.value))
            level?.let { levelDao.incrementLevel(it.id) }
        } catch (e: Exception) {
            Log.e(TAG, "Error increasing barbarian level", e)
        }
    }

    override suspend fun decreaseBarbarianLevel() {
        try {
            actDao.insert(BarbarianAct(type = ActsType.Gentleman.value))
            level?.let { levelDao.decrementLevel(it.id) }
        } catch (e: Exception) {
            Log.e(TAG, "Error decreasing barbarian level", e)
        }
    }

    override suspend fun increaseAndResetBarbarianLevel() {
        try {
            actDao.insert(BarbarianAct(type = ActsType.Barbarian.value))
            level?.let { levelDao.resetLevel(it.id) }
        } catch (e: Exception) {
            Log.e(TAG, "Error resetting barbarian level", e)
        }
    }

    override suspend fun getCurrentBarbarianLevel(): Int {
        return try {
            levelDao.getBarbarianLevel().firstOrNull()?.level ?: run {
                levelDao.updateBarbarianLevel(BarbarianLevel(level = 0))
                level = levelDao.getBarbarianLevel().firstOrNull()
                0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting barbarian level", e)
            0
        }
    }

    override suspend fun sync() {
        firebaseDataSource.getBarbarianLevel()?.let {
            toBarbarianLevelMapper.map(it).let { level ->
                levelDao.updateBarbarianLevel(level)
                this.level = level
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
    }

    companion object {
        private const val TAG = "BarbarianRepository"
    }
}
