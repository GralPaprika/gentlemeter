package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.data.cloud.model.ActDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper

class ActDocumentToBarbarianActMapper : Mapper<ActDocument, BarbarianAct> {
    override fun map(objectToMap: ActDocument): BarbarianAct {
        return BarbarianAct(
            id = objectToMap.id,
            type = objectToMap.type,
            timestamp = objectToMap.timestamp,
            comments = objectToMap.comments,
            synced = true,
        )
    }
}