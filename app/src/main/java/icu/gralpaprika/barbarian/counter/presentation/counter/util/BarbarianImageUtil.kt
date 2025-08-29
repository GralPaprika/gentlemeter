package icu.gralpaprika.barbarian.counter.presentation.counter.util

import icu.gralpaprika.barbarian.counter.R

object BarbarianImageUtil {
    
    fun getImageForBarbarianLevel(level: Int): Int {
        return when (level) {
            1 -> R.drawable.barbarian_level_1
            2 -> R.drawable.barbarian_level_2
            3 -> R.drawable.barbarian_level_3
            4 -> R.drawable.barbarian_level_4
            5 -> R.drawable.barbarian_level_5
            6 -> R.drawable.barbarian_level_6
            7 -> R.drawable.barbarian_level_7
            8 -> R.drawable.barbarian_level_8
            9 -> R.drawable.barbarian_level_9
            10 -> R.drawable.barbarian_level_10
            else -> R.drawable.barbarian_level_0
        }
    }
}
