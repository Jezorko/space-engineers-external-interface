package jezorko.github.sexi.shared

import org.slf4j.Logger
import java.io.File
import java.lang.Thread.currentThread


abstract class JsonFileRepository<T>(
    private val log: Logger,
    private val directoryPath: String,
    private val tClass: Class<T>,
    private val exampleResourcesDirectory: String?,
    private val exampleResourceNames: List<String> = emptyList()
) {

    open fun validate(value: T) {}

    fun save(name: String, value: T) = getFile(name).let { file ->
        validate(value)

        if (file.exists()) {
            log.info("overwriting file ${file.absolutePath}")
        }

        file.writeText(objectMapper.writeValueAsString(value))
    }

    fun read(name: String) = getFile(name).let { file ->
        log.info("reading file ${file.absolutePath}")
        if (!file.exists()) {
            throw IllegalStateException("file ${file.absolutePath} does not exist")
        } else if (!file.isFile) {
            throw IllegalStateException("${file.absolutePath} is not a file")
        }
        val valueAsString = file.readText()
        objectMapper.readValue(valueAsString, tClass)
    }

    fun exists(name: String) = getFile(name).exists()

    fun delete(name: String) = getFile(name).delete().let { isDeleted ->
        if (!isDeleted) throw IllegalStateException("failed to delete $name")
    }

    fun listFileNames() = directory.listFiles()!!.map { it.name }

    private fun getFile(name: String): File = File(directory.absolutePath + "/" + name)
    private val directory
        get() = File(directoryPath).apply {
            if (!exists()) {
                log.info("directory $absolutePath does not exist, creating")
                if (!mkdirs()) {
                    throw IllegalStateException("cannot create directory at $absolutePath")
                }
                loadExamples()
            } else if (!isDirectory) {
                throw IllegalStateException("$absolutePath is not a directory")
            }
        }

    private fun loadExamples() {
        if (exampleResourcesDirectory != null) {
            exampleResourceNames.forEach { exampleResourceName ->
                val exampleResourceValue = currentThread().contextClassLoader.getResource(
                    "$exampleResourcesDirectory/$exampleResourceName"
                )!!.readText()
                log.info("writing example file $exampleResourceName")
                save(exampleResourceName, objectMapper.readValue(exampleResourceValue, tClass))
            }
        }
    }

}