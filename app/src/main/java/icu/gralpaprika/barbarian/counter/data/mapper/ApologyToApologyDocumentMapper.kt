package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import icu.gralpaprika.barbarian.counter.data.cloud.model.ApologyDocument

class ApologyToApologyDocumentMapper : Mapper<Apology, ApologyDocument> {
    override fun map(objectToMap: Apology): ApologyDocument {
        return ApologyDocument(
            id = objectToMap.id,
            comments = objectToMap.comments,
            timestamp = objectToMap.timestamp,
        )
    }
}