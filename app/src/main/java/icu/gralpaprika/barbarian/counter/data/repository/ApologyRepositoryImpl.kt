package icu.gralpaprika.barbarian.counter.data.repository

import icu.gralpaprika.barbarian.counter.data.database.dao.ApologyDao
import icu.gralpaprika.barbarian.counter.data.database.model.Apology
import icu.gralpaprika.barbarian.counter.domain.repository.ApologyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApologyRepositoryImpl @Inject constructor(
    private val apologyDao: ApologyDao
) : ApologyRepository {
    override suspend fun insertApology(apology: Apology) {
        apologyDao.insert(apology)
    }

    override suspend fun getAllApologies(): List<Apology> {
        return apologyDao.getAllApologies()
    }

    override suspend fun getApologyCount(): Int {
        return apologyDao.getApologyCount()
    }
}
