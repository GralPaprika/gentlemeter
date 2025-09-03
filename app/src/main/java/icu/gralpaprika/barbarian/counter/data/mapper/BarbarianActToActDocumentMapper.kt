package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.data.cloud.model.ActDocument
import icu.gralpaprika.barbarian.counter.data.database.model.BarbarianAct
import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper

class BarbarianActToActDocumentMapper : Mapper<BarbarianAct, ActDocument> {
    override fun map(objectToMap: BarbarianAct): ActDocument {
        return ActDocument(
            id = objectToMap.id,
            type = objectToMap.type,
            comments = objectToMap.comments,
            timestamp = objectToMap.timestamp,
        )
    }
}