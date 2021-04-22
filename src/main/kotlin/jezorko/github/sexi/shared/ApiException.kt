package jezorko.github.sexi.shared

import io.netty.handler.codec.http.HttpResponseStatus

open class ApiException(internal val responseStatus: Int, val errorMessage: String, cause: Throwable? = null) :
    RuntimeException(errorMessage, cause) {

    constructor(responseStatus: HttpResponseStatus, errorMessage: String, cause: Throwable? = null)
            : this(responseStatus.code(), errorMessage, cause)

}