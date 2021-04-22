package jezorko.github.sexi.shared

import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

private typealias RouteRegistrationMethod = (Router, String) -> Route
typealias RoutingContextHandler = (RoutingContextWrapper) -> HttpResponse

interface ControllerRoute {

    val registrationMethod: RouteRegistrationMethod
    val urlMapping: String

    fun handle(context: RoutingContextWrapper): HttpResponse

    companion object {
        fun get(urlMapping: String, handler: RoutingContextHandler) = route(Router::get, urlMapping, handler)
        fun post(urlMapping: String, handler: RoutingContextHandler) = route(Router::post, urlMapping, handler)
        fun put(urlMapping: String, handler: RoutingContextHandler) = route(Router::put, urlMapping, handler)
        fun delete(urlMapping: String, handler: RoutingContextHandler) = route(Router::delete, urlMapping, handler)

        private fun route(
            registrationMethod: RouteRegistrationMethod,
            urlMapping: String,
            handler: RoutingContextHandler
        ) = object : ControllerRoute {
            override val registrationMethod get() = registrationMethod
            override val urlMapping get() = urlMapping
            override fun handle(context: RoutingContextWrapper): HttpResponse = handler(context)
        }
    }

}