package jezorko.github.sexi.templates

import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.ControllerRoute.Companion.delete
import jezorko.github.sexi.shared.ControllerRoute.Companion.get
import jezorko.github.sexi.shared.ControllerRoute.Companion.put
import jezorko.github.sexi.shared.HttpResponse
import jezorko.github.sexi.shared.HttpResponse.Companion.noContent
import jezorko.github.sexi.shared.HttpResponse.Companion.ok
import jezorko.github.sexi.shared.RoutingContextWrapper

const val templatesBasePath = "templates"
const val templateNameParam = "templateName"

class TemplatesController(private val templatesRepository: TemplatesRepository) : Controller {

    override fun routes() = listOf(
        get("/$templatesBasePath", this::getTemplateNames),
        get("/$templatesBasePath/:$templateNameParam", this::getTemplate),
        put("/$templatesBasePath/:$templateNameParam", this::putTemplate),
        delete("/$templatesBasePath/:$templateNameParam", this::deleteTemplate)
    )

    private fun getTemplateNames(): HttpResponse {
        return ok(templatesRepository.listFileNames())
    }

    private fun getTemplate(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(templateNameParam)
        return ok(templatesRepository.read(templateName))
    }

    private fun putTemplate(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(templateNameParam)
        templatesRepository.save(templateName, context.getBody(LayoutTemplate::class))
        return noContent()
    }

    private fun deleteTemplate(context: RoutingContextWrapper): HttpResponse {
        val templateName = context.getPathParam(templateNameParam)
        templatesRepository.delete(templateName)
        return noContent()
    }

}