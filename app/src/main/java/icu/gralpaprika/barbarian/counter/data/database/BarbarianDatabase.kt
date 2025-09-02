package icu.gralpaprika.barbarian.counter.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import icu.gralpaprika.barbarian.counter.data.database.dao.ApologyDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianActDao
import icu.gralpaprika.barbarian.counter.data.database.dao.BarbarianLevelDao
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel

@Database(entities = [BarbarianAct::class, BarbarianLevel::class, Apology::class], version = 2)
abstract class BarbarianDatabase : RoomDatabase() {
    abstract fun barbarianActDao(): BarbarianActDao
    abstract fun barbarianLevelDao(): BarbarianLevelDao
    abstract fun apologyDao(): ApologyDao
}
