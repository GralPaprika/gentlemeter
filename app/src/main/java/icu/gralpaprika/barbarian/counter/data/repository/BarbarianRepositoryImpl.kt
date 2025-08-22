package icu.gralpaprika.barbarian.counter.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import icu.gralpaprika.barbarian.counter.domain.repository.BarbarianRepository
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class BarbarianRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BarbarianRepository {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREF_NAME, Context.MODE_PRIVATE
    )

    override val maxBarbarianLevel =
        sharedPreferences.getInt(MAX_BARBARIAN_LEVEL, 10)

    override val minBarbarianLevel = 0
    
    companion object {
        private const val PREF_NAME = "barbarian_counter_prefs"
        private const val BARBARIAN_LEVEL = "barbarian_level"
        private const val MAX_BARBARIAN_LEVEL = "max_barbarian_level"
        private const val TAG = "BarbarianRepository"
    }
    
    override fun increaseBarbarianLevel() {
        val level = getCurrentBarbarianLevel() + 1
        saveBarbarianLevel(level)
    }
    
    override fun decreaseBarbarianLevel() {
        val level = getCurrentBarbarianLevel() - 1
        saveBarbarianLevel(level)
    }

    override fun resetBarbarianLevel() {
        saveBarbarianLevel(0)
    }

    override fun getCurrentBarbarianLevel(): Int =
        sharedPreferences.getInt(BARBARIAN_LEVEL, 0)
    
    private fun saveBarbarianLevel(level: Int) {
        try {
            sharedPreferences.edit {
                putInt(BARBARIAN_LEVEL, level)
                    .putInt(MAX_BARBARIAN_LEVEL, maxBarbarianLevel)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving barbarian level", e)
        }
    }
}
