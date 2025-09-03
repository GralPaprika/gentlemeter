package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.data.cloud.model.LevelDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper

class BarbarianLevelToLevelDocumentMapper : Mapper<BarbarianLevel, LevelDocument> {
    override fun map(objectToMap: BarbarianLevel): LevelDocument {
        return LevelDocument(
            id = objectToMap.id,
            level = objectToMap.level,
        )
    }
}