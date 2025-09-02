package icu.gralpaprika.barbarian.counter.domain.mapper

interface Mapper<InClass, OutClass> {
    fun map(objectToMap: InClass): OutClass
}