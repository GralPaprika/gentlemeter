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

    override fun getAllApologies(): Flow<List<Apology>> {
        return apologyDao.getAllApologies()
    }

    override fun getApologyCount(): Flow<Int> {
        return apologyDao.getApologyCount()
    }
}
