package jezorko.github.sexi.shared

import org.slf4j.LoggerFactory.getLogger

private val log = getLogger(ConfigurationProperty::class.java)!!

/**
 * Port that server will be listening on.
 */
val SERVER_PORT = ConfigurationProperty("SERVER_PORT", 3_000, String::toInt)

/**
 * When this variable is set to true, frontend files will be served from the resources directory instead of
 * classpath.
 */
val WATCH_FRONTEND_CHANGES = ConfigurationProperty("WATCH_FRONTEND_CHANGES", false, String::toBoolean)

/**
 * Directory where templates will be stored.
 */
val TEMPLATES_DIRECTORY = ConfigurationProperty("TEMPLATES_DIRECTORY", "templates", { it })

/**
 * Directory where interfaces will be stored.
 */
val INTERFACES_DIRECTORY = ConfigurationProperty("INTERFACES_DIRECTORY", "interfaces", { it })

data class ConfigurationProperty<T>(
    private val name: String,
    private val defaultValue: T,
    private val valueFromString: (String) -> T
) {

    val value: T

    init {
        val environmentValue = System.getenv(name)
        value = if (environmentValue != null) {
            valueFromString(environmentValue)
        } else {
            log.info("property $name not overridden by environment variables, using default value: $defaultValue")
            defaultValue
        }
    }

}