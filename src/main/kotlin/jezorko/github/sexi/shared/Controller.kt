package jezorko.github.sexi.shared

import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import org.slf4j.LoggerFactory


private val log = LoggerFactory.getLogger(Controller::class.java)

interface Controller {

    fun routes(): Collection<ControllerRoute>

    fun registerIn(router: Router) {
        routes().forEach { route ->
            route.registrationMethod(router, route.urlMapping)
                .handler { context ->
                    try {
                        val response = route.handle(RoutingContextWrapper(context))
                        context.response().statusCode = response.status
                        if (response.body == null) {
                            context.response().end()
                        } else {
                            context.response()
                                .putHeader("Content-Type", response.contentType)
                                .end(
                                    if (response.contentType?.startsWith("application/json") == true)
                                        Json.encode(response.body)
                                    else
                                        response.body.toString()
                                )
                        }
                    } catch (exception: Exception) {
                        if (exception is ApiException) {
                            context.response()
                                .setStatusCode(exception.responseStatus)
                                .end(exception.message)
                        } else {
                            log.error("Controller method handling failed", exception)
                            context.response()
                                .setStatusCode(500)
                                .end()
                        }
                    }
                }
        }
    }

}