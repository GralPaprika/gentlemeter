package icu.gralpaprika.barbarian.counter.presentation.util

import icu.gralpaprika.barbarian.counter.R

object BarbarianImageUtil {
    
    fun getImageForBarbarianLevel(level: Int): Int {
        return when (level) {
            0 -> R.drawable.gentleman_level_0
            1 -> R.drawable.gentleman_level_1
            2 -> R.drawable.gentleman_level_2
            3 -> R.drawable.gentleman_level_3
            4 -> R.drawable.gentleman_level_4
            5 -> R.drawable.gentleman_level_5
            6 -> R.drawable.gentleman_level_6
            7 -> R.drawable.gentleman_level_7
            8 -> R.drawable.gentleman_level_8
            9 -> R.drawable.gentleman_level_9
            10 -> R.drawable.gentleman_level_10
            else -> R.drawable.gentleman_level_0
        }
    }
}
