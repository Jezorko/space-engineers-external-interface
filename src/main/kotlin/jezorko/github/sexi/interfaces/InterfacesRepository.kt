package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.JsonFileRepository
import jezorko.github.sexi.shared.TEMPLATES_DIRECTORY

class TemplatesRepository : JsonFileRepository<Interface>(
    TEMPLATES_DIRECTORY.value,
    Interface::class.java
) {

    override fun onInit() {}

}