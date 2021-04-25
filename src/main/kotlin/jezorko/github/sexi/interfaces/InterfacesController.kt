package jezorko.github.sexi.interfaces

import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.ControllerRoute.Companion.delete
import jezorko.github.sexi.shared.ControllerRoute.Companion.get
import jezorko.github.sexi.shared.ControllerRoute.Companion.put
import jezorko.github.sexi.shared.HttpResponse
import jezorko.github.sexi.shared.HttpResponse.Companion.noContent
import jezorko.github.sexi.shared.HttpResponse.Companion.ok
import jezorko.github.sexi.shared.RoutingContextWrapper
import jezorko.github.sexi.templates.templateNameParam
import jezorko.github.sexi.templates.templatesBasePath

private const val interfacesBasePath = "interfaces"
private const val interfaceNameParam = "interfaceName"

class InterfacesController(private val interfacesRepository: InterfacesRepository) : Controller {

    override fun routes() = listOf(
        get("/$interfacesBasePath", this::getInterfaceNames),
        get("/$interfacesBasePath/:$interfaceNameParam", this::getInterface),
        get("/$templatesBasePath/:$templateNameParam/$interfacesBasePath", this::getAllInterfacesForTemplate),
        put("/$interfacesBasePath/:$interfaceNameParam", this::putInterface),
        delete("/$interfacesBasePath/:$interfaceNameParam", this::deleteInterface)
    )

    private fun getInterfaceNames(): HttpResponse {
        return ok(interfacesRepository.listFileNames())
    }

    private fun getInterface(context: RoutingContextWrapper): HttpResponse {
        val interfaceName = context.getPathParam(interfaceNameParam)
        return ok(interfacesRepository.read(interfaceName))
    }

    private fun getAllInterfacesForTemplate(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(templateNameParam)
        return ok(interfacesRepository.listFileNamesForTemplate(templateName))
    }

    private fun putInterface(context: RoutingContextWrapper): HttpResponse {
        val interfaceName = context.getPathParam(interfaceNameParam)
        interfacesRepository.save(interfaceName, context.getBody(Interface::class))
        return noContent()
    }

    private fun deleteInterface(context: RoutingContextWrapper): HttpResponse {
        val interfaceName = context.getPathParam(interfaceNameParam)
        interfacesRepository.delete(interfaceName)
        return noContent()
    }

}