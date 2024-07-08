package kwi.tool.androidsockettask.domain.repository

import kwi.tool.androidsockettask.data.db.datasource.DataDao
import kwi.tool.androidsockettask.data.db.entity.DataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvestRepository @Inject constructor(private val dao: DataDao) {

    suspend fun insertData(data: DataEntity) = dao.insertData(data)
    suspend fun getAllData(): Flow<List<DataEntity>> =  dao.getAllData()
    suspend fun updateData(data: DataEntity) =  dao.update(data)

}