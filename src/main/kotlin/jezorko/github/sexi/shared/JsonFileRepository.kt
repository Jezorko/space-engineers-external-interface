package jezorko.github.sexi.shared

import org.slf4j.LoggerFactory
import java.io.File


abstract class JsonFileRepository<T>(
    private val directoryPath: String,
    private val tClass: Class<T>
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    abstract fun onInit()

    fun save(name: String, value: T) = getFile(name).let { file ->
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

    fun listTemplateNames() = directory.listFiles()!!.map { it.name }

    private fun getFile(name: String): File = File(directory.absolutePath + "/" + name)
    private val directory
        get() = File(directoryPath).apply {
            if (!exists()) {
                log.info("directory $absolutePath does not exist, creating")
                if (!mkdirs()) {
                    throw IllegalStateException("cannot create directory at $absolutePath")
                }
                onInit()
            } else if (!isDirectory) {
                throw IllegalStateException("$absolutePath is not a directory")
            }
        }

}