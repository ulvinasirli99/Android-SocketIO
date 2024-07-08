package kwi.tool.androidsockettask.data.db.datasource

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import kwi.tool.androidsockettask.data.db.entity.DataEntity
import kwi.tool.androidsockettask.core.constant.Constants.TABLE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataDaoImpl(private val database: SQLiteDatabase) : DataDao {

    override suspend fun insertData(data: DataEntity) {
        val values = ContentValues().apply {
            put("up_down", data.up_down)
            put("brand", data.brand)
            put("openingPrice", data.openingPrice)
            put("lowPrice", data.lowPrice)
            put("currentPrice", data.currentPrice)
            put("currentPrice", data.currentPrice)
            put("highPrice", data.highPrice)
            put("valueOfSharesInDay", data.valueOfSharesInDay)
        }
        database.insert(TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    override suspend fun getAllData(): Flow<MutableList<DataEntity>> {
        return flow {
            val cursor = database.rawQuery("SELECT * FROM $TABLE_NAME", null)
            val dataList = mutableListOf<DataEntity>()
            while (cursor.moveToNext()) {
                val upDown = cursor.getString(cursor.getColumnIndex("up_down"))
                val brand = cursor.getString(cursor.getColumnIndex("brand"))
                val openingPrice = cursor.getString(cursor.getColumnIndex("openingPrice"))
                val currentPrice = cursor.getString(cursor.getColumnIndex("currentPrice"))
                val lowPrice = cursor.getString(cursor.getColumnIndex("lowPrice"))
                val highPrice = cursor.getString(cursor.getColumnIndex("highPrice"))
                val valueOfSharesInDay = cursor.getInt(cursor.getColumnIndex("valueOfSharesInDay"))
                dataList.add(DataEntity(upDown,brand, openingPrice, currentPrice,lowPrice,highPrice,valueOfSharesInDay))
            }
            emit(dataList)
            cursor.close()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun update(data: DataEntity) {
        val values = ContentValues().apply {
            put("up_down", data.up_down)
            put("brand", data.brand)
            put("openingPrice", data.openingPrice)
            put("lowPrice", data.lowPrice)
            put("currentPrice", data.currentPrice)
            put("currentPrice", data.currentPrice)
            put("highPrice", data.highPrice)
            put("valueOfSharesInDay", data.valueOfSharesInDay)
        }
        database.update(TABLE_NAME,values,"id=?",null)
    }
}
