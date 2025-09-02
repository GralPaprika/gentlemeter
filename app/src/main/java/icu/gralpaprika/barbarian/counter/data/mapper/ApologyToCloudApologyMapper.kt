package icu.gralpaprika.barbarian.counter.data.mapper

import icu.gralpaprika.barbarian.counter.domain.mapper.Mapper
import icu.gralpaprika.barbarian.counter.data.database.model.Apology as DatabaseApology
import icu.gralpaprika.barbarian.counter.data.cloud.model.Apology as CloudApology

class ApologyToCloudApologyMapper: Mapper<DatabaseApology, CloudApology> {
    override fun map(objectToMap: DatabaseApology): CloudApology {
        return CloudApology(
            id = objectToMap.id,
            comments = objectToMap.comments,
            timestamp = objectToMap.timestamp,
        )
    }
}