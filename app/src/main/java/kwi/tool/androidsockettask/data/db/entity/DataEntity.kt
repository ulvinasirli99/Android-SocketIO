package kwi.tool.androidsockettask.data.db.entity

import androidx.room.PrimaryKey
import kwi.tool.androidsockettask.domain.model.DataResponse

data class DataEntity(
    var up_down : String,
    val brand: String,
    val openingPrice: String,
    val currentPrice: String,
    val lowPrice: String,
    val highPrice : String,
    val valueOfSharesInDay: Int,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
fun DataEntity.toDataResponse(): DataResponse {
    return DataResponse(
        up_down = up_down,
        brand = brand,
        openingPrice = openingPrice,
        currentPrice =currentPrice,
        lowPrice =lowPrice,
        highPrice = highPrice,
        valueOfSharesInDay = valueOfSharesInDay
    )
}