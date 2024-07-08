package kwi.tool.androidsockettask.core.utils

sealed class ApiStatus<out T> {
    data class Update<out T>(val value: T) : ApiStatus<T>()
    data class Connected<out T>(val isConnected: Boolean) : ApiStatus<T>()
    data class Error(val message: String) : ApiStatus<Nothing>()
}