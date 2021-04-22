package jezorko.github.sexi

import io.vertx.core.Vertx.vertx
import jezorko.github.sexi.keyboard.KeyboardController
import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.templates.TemplatesRepository
import org.slf4j.LoggerFactory.getLogger

private val log = getLogger("main")


fun main() {
    val templatesRepository = TemplatesRepository()
    val testTemplate = templatesRepository.read("test.json")
    templatesRepository.save("test.json", testTemplate.copy(defaultFont = "overwritten"))

    val controllers = listOf<Controller>(
        KeyboardController()
    )

    vertx().deployVerticle(ServerVerticle(controllers))
        .onSuccess { verticleId -> log.info("verticle $verticleId deployed") }
        .onFailure { error -> log.error("failed to deploy verticle", error) }
}