package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.data.cloud.model.LevelDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper

class LevelDocumentToBarbarianLevelMapper: Mapper<LevelDocument, BarbarianLevel> {
    override fun map(objectToMap: LevelDocument): BarbarianLevel {
        return BarbarianLevel(
            id = objectToMap.id,
            level = objectToMap.level,
            synced = true,
        )
    }
}