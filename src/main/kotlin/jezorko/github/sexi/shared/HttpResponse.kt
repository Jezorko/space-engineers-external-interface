package jezorko.github.sexi.shared

import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.codec.http.HttpResponseStatus.*

data class HttpResponse(
    val status: Int,
    val body: Any? = null,
    val contentType: String? = "application/json"
) {

    constructor(status: HttpResponseStatus, body: Any? = null, contentType: String? = "application/json")
            : this(status.code(), body, contentType)

    companion object {
        fun ok(body: Any?, contentType: String? = "application/json") = HttpResponse(OK, body, contentType)
        fun badRequest(message: String) = HttpResponse(BAD_REQUEST, message)
        fun notFound() = HttpResponse(NOT_FOUND, null)
        fun created(body: Any? = null) = HttpResponse(CREATED, body)
        fun noContent() = HttpResponse(NO_CONTENT, null)
    }

}