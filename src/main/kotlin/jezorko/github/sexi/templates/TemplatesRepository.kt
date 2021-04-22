package jezorko.github.sexi.templates

import jezorko.github.sexi.shared.JsonFileRepository
import jezorko.github.sexi.shared.TEMPLATES_DIRECTORY
import jezorko.github.sexi.shared.objectMapper
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

private const val DEFAULT_TEMPLATES_RESOURCE_DIRECTORY = "default_templates"

class TemplatesRepository : JsonFileRepository<LayoutTemplate>(
    TEMPLATES_DIRECTORY.value,
    LayoutTemplate::class.java
) {

    override fun onInit() {
        getDefaultTemplates().forEach { (templateName, templateAsString) ->
            save(templateName, objectMapper.readValue(templateAsString, LayoutTemplate::class.java))
        }
    }

    private fun getDefaultTemplates(): Map<String, String> = getDefaultTemplateNames().map { templateName ->
        templateName to classLoader.getResource(
            "$DEFAULT_TEMPLATES_RESOURCE_DIRECTORY/$templateName"
        )!!.readText()
    }.toMap()

    private fun getDefaultTemplateNames(): List<String> = getDefaultTemplateNamesAsStream().use {
        return if (it == null) emptyList()
        else BufferedReader(InputStreamReader(it)).readLines()
    }

    private fun getDefaultTemplateNamesAsStream(): InputStream? =
        classLoader.getResourceAsStream(DEFAULT_TEMPLATES_RESOURCE_DIRECTORY)
            ?: DEFAULT_TEMPLATES_RESOURCE_DIRECTORY::class.java.getResourceAsStream(DEFAULT_TEMPLATES_RESOURCE_DIRECTORY)

    private val classLoader get() = Thread.currentThread().contextClassLoader

}