package jezorko.github.sexi.shared

import io.vertx.ext.web.RoutingContext
import kotlin.reflect.KClass


class RoutingContextWrapper(private val delegate: RoutingContext) {

    fun getPathParam(paramName: String): String = delegate.pathParam(paramName)
        ?: throw IllegalStateException("path param $paramName does not exist")

    fun <T : Any> getBody(bodyClass: KClass<T>): T = objectMapper.readValue(bodyAsString, bodyClass.java)

    val bodyAsString get():String = delegate.bodyAsString

}