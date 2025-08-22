package icu.gralpaprika.barbarian.counter.data.repository

import android.util.Log
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianActDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianLevelDao
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.domain.model.ActsType
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarbarianRepositoryImpl @Inject constructor(
    private val barbarianActDao: BarbarianActDao,
    private val barbarianLevelDao: BarbarianLevelDao,
) : BarbarianRepository {

    override val maxBarbarianLevel = 10

    override val minBarbarianLevel = 0

    override suspend fun increaseBarbarianLevel() {
        try {
            barbarianActDao.insert(BarbarianAct(type = ActsType.Barbarian.value))
            barbarianLevelDao.incrementLevel()
        } catch (e: Exception) {
            Log.e(TAG, "Error increasing barbarian level", e)
        }
    }

    override suspend fun decreaseBarbarianLevel() {
        try {
            barbarianActDao.insert(BarbarianAct(type = ActsType.Gentleman.value))
            barbarianLevelDao.decrementLevel()
        } catch (e: Exception) {
            Log.e(TAG, "Error decreasing barbarian level", e)
        }
    }

    override suspend fun increaseAndResetBarbarianLevel() {
        try {
            barbarianActDao.insert(BarbarianAct(type = ActsType.Barbarian.value))
            barbarianLevelDao.resetLevel()
        } catch (e: Exception) {
            Log.e(TAG, "Error resetting barbarian level", e)
        }
    }

    override suspend fun getCurrentBarbarianLevel(): Int {
        return try {
            barbarianLevelDao.getBarbarianLevel().firstOrNull()?.level ?: run {
                barbarianLevelDao.updateBarbarianLevel(BarbarianLevel(level = 0))
                0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting barbarian level", e)
            0
        }
    }

    companion object {
        private const val TAG = "BarbarianRepository"
    }
}
