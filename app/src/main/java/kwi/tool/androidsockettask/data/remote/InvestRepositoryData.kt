package kwi.tool.androidsockettask.data.remote

import android.util.Log
import com.google.gson.*
import kwi.tool.androidsockettask.core.utils.ApiStatus
import kwi.tool.androidsockettask.domain.model.DataModel
import kwi.tool.androidsockettask.domain.model.DataResponse
import io.socket.client.Socket
import io.socket.client.Socket.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class InvestRepositoryData @Inject constructor(private var socket: Socket) {

    private val TAG = "SocketIORepository"

    fun observeWebSocketData():Flow<ApiStatus<List<DataResponse>>>{
        socket.connect()

        return callbackFlow {
            socket.on(EVENT_CONNECT){
                trySend(ApiStatus.Connected(true))
            }
            socket.on(EVENT_DISCONNECT) { error ->
                trySend(ApiStatus.Error(error[0].toString()))
            }
            socket.on(EVENT_CONNECT_ERROR) { error ->
                trySend(ApiStatus.Error(error[0].toString()))
            }
            socket.on("message") { data ->
                val result = data[0].toString()
                val dataFromJson = Gson().fromJson(result, DataModel::class.java)
                Log.d(TAG, "observeSocketIOData: ->>> ${dataFromJson.result}")
                trySend(ApiStatus.Update(dataFromJson.result))
            }

            awaitClose { socket.close() }
        }
    }
}

