package kwi.tool.androidsockettask.domain.usecases.db_usecases

import kwi.tool.androidsockettask.data.db.entity.DataEntity
import kwi.tool.androidsockettask.domain.repository.InvestRepository
import javax.inject.Inject

class InsertDataUseCases @Inject constructor(
    private var dataRepository: InvestRepository
) {
    suspend  operator fun invoke(dataEntity: DataEntity){
        dataRepository.insertData(dataEntity)
    }
}