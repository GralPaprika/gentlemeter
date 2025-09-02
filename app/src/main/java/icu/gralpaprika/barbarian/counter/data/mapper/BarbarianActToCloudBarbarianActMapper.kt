package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct as DatabaseBarbarianAct
import icu.gralpaprika.barbarian.counter.data.cloud.model.BarbarianAct as CloudBarbarianAct

class BarbarianActToCloudBarbarianActMapper: Mapper<DatabaseBarbarianAct, CloudBarbarianAct> {
    override fun map(objectToMap: DatabaseBarbarianAct): CloudBarbarianAct {
        return CloudBarbarianAct(
            id = objectToMap.id,
            type = objectToMap.type,
            comments = objectToMap.comments,
            timestamp = objectToMap.timestamp,
        )
    }
}