package kwi.tool.androidsockettask.data.db.datasource

import kwi.tool.androidsockettask.data.db.entity.DataEntity
import kotlinx.coroutines.flow.Flow

interface DataDao {
    suspend fun insertData(data: DataEntity)
    suspend fun getAllData(): Flow<List<DataEntity>>
    suspend fun update(data: DataEntity)
}