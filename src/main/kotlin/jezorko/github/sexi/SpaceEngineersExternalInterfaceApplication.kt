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
    val templatesRepository = TemplatesRepository()
    val controllers = listOf(
        KeyboardController(),
        TemplatesController(templatesRepository),
        InterfacesController(InterfacesRepository(templatesRepository))
    )

    vertx().deployVerticle(ServerVerticle(controllers))
        .onSuccess { verticleId -> log.info("verticle $verticleId deployed") }
        .onFailure { error -> log.error("failed to deploy verticle", error) }
}