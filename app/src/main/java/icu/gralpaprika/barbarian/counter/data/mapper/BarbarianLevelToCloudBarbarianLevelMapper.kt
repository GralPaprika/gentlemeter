package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianLevel as DatabaseBarbarianLevel
import icu.gralpaprika.barbarian.counter.data.cloud.model.BarbarianLevel as CloudBarbarianLevel

class BarbarianLevelToCloudBarbarianLevelMapper: Mapper<DatabaseBarbarianLevel, CloudBarbarianLevel> {
    override fun map(objectToMap: DatabaseBarbarianLevel): CloudBarbarianLevel {
        return CloudBarbarianLevel(
            id = objectToMap.id,
            level = objectToMap.level,
        )
    }
}