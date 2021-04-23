package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.INTERFACES_DIRECTORY
import jezorko.github.sexi.shared.JsonFileRepository

class InterfacesRepository : JsonFileRepository<Interface>(
    INTERFACES_DIRECTORY.value,
    Interface::class.java
) {

    override fun onInit() {}

}