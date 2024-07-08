package kwi.tool.androidsockettask.domain.model

import com.google.gson.annotations.SerializedName
import kwi.tool.androidsockettask.domain.model.DataResponse

data class DataModel(
    @field:SerializedName("result")
    val result: List<DataResponse>
)