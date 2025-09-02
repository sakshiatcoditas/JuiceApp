package com.example.juice.data

import kotlinx.coroutines.flow.Flow

class RoomJuiceRepository(private val juiceDao: JuiceDao) {
    val juicesStream: Flow<List<Juice>> = juiceDao.getAll()
    fun getJuiceStream(id: Long): Flow<Juice?> = juiceDao.get(id)
    suspend fun addJuice(juice: Juice) = juiceDao.insert(juice)
    suspend fun deleteJuice(juice: Juice) = juiceDao.delete(juice)
    suspend fun updateJuice(juice: Juice) = juiceDao.update(juice)
}
