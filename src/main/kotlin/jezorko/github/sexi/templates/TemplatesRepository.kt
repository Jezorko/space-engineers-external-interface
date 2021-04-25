package jezorko.github.sexi.templates

import jezorko.github.sexi.shared.JsonFileRepository
import jezorko.github.sexi.shared.TEMPLATES_DIRECTORY
import org.slf4j.LoggerFactory.getLogger


class TemplatesRepository : JsonFileRepository<LayoutTemplate>(
    getLogger(TemplatesRepository::class.java),
    TEMPLATES_DIRECTORY.value,
    LayoutTemplate::class.java,
    "examples/templates",
    listOf("caracara.json")
)