package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.ControllerRoute.Companion.delete
import jezorko.github.sexi.shared.ControllerRoute.Companion.get
import jezorko.github.sexi.shared.ControllerRoute.Companion.put
import jezorko.github.sexi.shared.HttpResponse
import jezorko.github.sexi.shared.HttpResponse.Companion.noContent
import jezorko.github.sexi.shared.HttpResponse.Companion.ok
import jezorko.github.sexi.shared.RoutingContextWrapper

private const val interfaceNameParam = "interfaceName"

class InterfacesController(private val interfacesRepository: InterfacesRepository) : Controller {

    override fun routes() = listOf(
        get("/interfaces", this::getInterfaceNames),
        get("/interfaces/:$interfaceNameParam", this::getInterface),
        put("/interfaces/:$interfaceNameParam", this::putInterface),
        delete("/interfaces/:$interfaceNameParam", this::deleteInterface)
    )

    private fun getInterfaceNames(): HttpResponse {
        return ok(interfacesRepository.listFileNames())
    }

    private fun getInterface(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(interfaceNameParam)
        return ok(interfacesRepository.read(templateName))
    }

    private fun putInterface(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(interfaceNameParam)
        interfacesRepository.save(templateName, context.getBody(Interface::class))
        return noContent()
    }

    private fun deleteInterface(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(interfaceNameParam)
        interfacesRepository.delete(templateName)
        return noContent()
    }

}