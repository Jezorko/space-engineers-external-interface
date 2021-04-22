package jezorko.github.sexi

import io.vertx.core.Vertx.vertx
import jezorko.github.sexi.interfaces.InterfacesController
import jezorko.github.sexi.interfaces.InterfacesRepository
import jezorko.github.sexi.keyboard.KeyboardController
import jezorko.github.sexi.templates.TemplatesController
import jezorko.github.sexi.templates.TemplatesRepository
import org.slf4j.LoggerFactory.getLogger

private val log = getLogger("main")


fun main() {
    val controllers = listOf(
        KeyboardController(),
        TemplatesController(TemplatesRepository()),
        InterfacesController(InterfacesRepository())
    )

    vertx().deployVerticle(ServerVerticle(controllers))
        .onSuccess { verticleId -> log.info("verticle $verticleId deployed") }
        .onFailure { error -> log.error("failed to deploy verticle", error) }
}