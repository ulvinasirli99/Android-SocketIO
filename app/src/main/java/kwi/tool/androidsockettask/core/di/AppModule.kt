package kwi.tool.androidsockettask.core.di


import android.app.Application
import kwi.tool.androidsockettask.core.constant.Constants.BASE_URL
import kwi.tool.androidsockettask.core.constant.Constants.PATH_ENDPOINT
import kwi.tool.androidsockettask.data.db.datasource.DataDao
import kwi.tool.androidsockettask.data.db.helper.DatabaseHelper
import kwi.tool.androidsockettask.data.remote.InvestRepositoryData
import kwi.tool.androidsockettask.domain.repository.InvestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.Polling
import io.socket.engineio.client.transports.WebSocket
import java.net.URI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesSocket():Socket {
        val options = IO.Options().apply {
            path = PATH_ENDPOINT
            transports = arrayOf(WebSocket.NAME, Polling.NAME)
            upgrade = true
            rememberUpgrade = false
            reconnection = true
            reconnectionDelay = 5000
            timeout = 20000
        }
        val uri = URI.create(BASE_URL)
        return IO.socket(uri, options)
    }

    @Singleton
    @Provides
    fun providesRepository(socket: Socket): InvestRepositoryData {
        return InvestRepositoryData(socket)
    }

    @Singleton
    @Provides
    fun provideDatabaseHelper(application: Application): DatabaseHelper {
        return DatabaseHelper(application)
    }

    @Singleton
    @Provides
    fun provideDataRepository(dao: DataDao): InvestRepository {
        return InvestRepository(dao)
    }

    @Singleton
    @Provides
    fun provideDataDao(helper: DatabaseHelper): DataDao {
        return helper.dao()
    }

}