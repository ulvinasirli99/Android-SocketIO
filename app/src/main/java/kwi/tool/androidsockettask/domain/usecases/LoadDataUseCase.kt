package kwi.tool.androidsockettask.domain.usecases


import kwi.tool.androidsockettask.core.utils.ApiStatus
import kwi.tool.androidsockettask.domain.model.DataResponse
import kwi.tool.androidsockettask.data.remote.InvestRepositoryData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: InvestRepositoryData) {
    operator fun invoke(): Flow<ApiStatus<List<DataResponse>>> {
        return repository.observeWebSocketData()
    }
}