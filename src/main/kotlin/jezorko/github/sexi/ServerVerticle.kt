package jezorko.github.sexi

import io.vertx.core.AbstractVerticle
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod.*
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.StaticHandler
import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.SERVER_PORT
import jezorko.github.sexi.shared.WATCH_FRONTEND_CHANGES
import org.slf4j.LoggerFactory.getLogger

private val log = getLogger(ServerVerticle::class.java)


class ServerVerticle(private val controllers: Collection<Controller>) : AbstractVerticle() {

    override fun start() {
        val router = router(vertx)

        router.route().handler(
            CorsHandler.create("*")
                .allowedMethods(setOf(GET, POST, PUT, DELETE, OPTIONS))
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type")
        )
        router.route().handler(BodyHandler.create())

        controllers.forEach { controller -> controller.registerIn(router) }

        val frontendFilesPath = if (WATCH_FRONTEND_CHANGES.value) "src/main/resources/frontend" else "frontend"
        router.route("/*").handler(StaticHandler.create(frontendFilesPath))

        router.errorHandler(500) { log.error("request failed", it.failure()) }

        val port = SERVER_PORT.value
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onSuccess { log.info("server started listening on port $port") }
            .onFailure { error -> log.error("failed to start server", error) }
    }

}