package kwi.tool.androidsockettask.data.db.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kwi.tool.androidsockettask.data.db.datasource.DataDaoImpl
import kwi.tool.androidsockettask.core.constant.Constants.DATABASE_NAME
import kwi.tool.androidsockettask.core.constant.Constants.DATABASE_VERSION
import kwi.tool.androidsockettask.core.constant.Constants.TABLE_NAME
import kwi.tool.androidsockettask.data.db.datasource.DataDao

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (" +
                "id INTEGER PRIMARY KEY, " +
                "up_down TEXT, " +
                "brand Text,"+
                "openingPrice TEXT,"+
                "currentPrice TEXT,"+
                "lowPrice TEXT,"+
                "highPrice TEXT,"+
                "valueOfSharesInDay Integer)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun dao(): DataDao {
        return DataDaoImpl(writableDatabase)
    }
}
