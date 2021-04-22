package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.JsonFileRepository

class InterfacesRepository : JsonFileRepository<Interface>(
    "interfaces",
    Interface::class.java
) {

    override fun onInit() {}

}