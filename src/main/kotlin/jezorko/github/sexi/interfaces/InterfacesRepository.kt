package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.INTERFACES_DIRECTORY
import jezorko.github.sexi.shared.JsonFileRepository
import jezorko.github.sexi.templates.TemplatesRepository
import org.slf4j.LoggerFactory.getLogger

class InterfacesRepository(private val templatesRepository: TemplatesRepository) : JsonFileRepository<Interface>(
    getLogger(InterfacesRepository::class.java),
    INTERFACES_DIRECTORY.value,
    Interface::class.java,
    "examples/interfaces",
    listOf("caracara_mk1.json")
) {

    override fun validate(value: Interface) {
        if (!templatesRepository.exists(value.templateName)) {
            throw IllegalArgumentException("template ${value.templateName} specified in the interface does not exist")
        }
    }

}