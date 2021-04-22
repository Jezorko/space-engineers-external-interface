package jezorko.github.sexi

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router.router
import io.vertx.ext.web.handler.StaticHandler
import jezorko.github.sexi.shared.SERVER_PORT
import jezorko.github.sexi.shared.WATCH_FRONTEND_CHANGES
import org.slf4j.LoggerFactory.getLogger

private val log = getLogger(ServerVerticle::class.java)


class ServerVerticle : AbstractVerticle() {

    override fun start() {
        val router = router(vertx)

        val frontendFilesPath = if (WATCH_FRONTEND_CHANGES.value) "src/main/resources/frontend" else "frontend"
        router.route("/*").handler(StaticHandler.create(frontendFilesPath))

        val port = SERVER_PORT.value
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onSuccess { log.info("server started listening on port $port") }
            .onFailure { error -> log.error("failed to start server", error)}
    }

}