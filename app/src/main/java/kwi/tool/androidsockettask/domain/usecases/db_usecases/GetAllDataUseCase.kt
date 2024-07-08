package kwi.tool.androidsockettask.domain.usecases.db_usecases

import kwi.tool.androidsockettask.data.db.entity.DataEntity
import kwi.tool.androidsockettask.domain.repository.InvestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDataUseCase @Inject constructor(
    private val dataRepository: InvestRepository
){
    suspend operator fun invoke(): Flow<List<DataEntity>>{
        return dataRepository.getAllData()
    }
}