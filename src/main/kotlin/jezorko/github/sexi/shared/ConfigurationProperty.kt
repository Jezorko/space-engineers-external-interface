package jezorko.github.sexi.shared

import org.slf4j.LoggerFactory.getLogger

private val log = getLogger(ConfigurationProperty::class.java)!!

/**
 * Port that server will be listening on.
 */
val SERVER_PORT = ConfigurationProperty("SERVER_PORT", 3_000, String::toInt)

/**
 * When this variable is set to true, frontend files will be served from the resources directory instead of
 * classpath. Will probably not work when running from a JAR file.
 */
val WATCH_FRONTEND_CHANGES = ConfigurationProperty("WATCH_FRONTEND_CHANGES", false, String::toBoolean)

/**
 * Directory where templates will be stored.
 */
val TEMPLATES_DIRECTORY = ConfigurationProperty("TEMPLATES_DIRECTORY", "templates", { it }, {
    if (it.isBlank()) throw IllegalArgumentException("templates directory must be defined")
})

/**
 * Directory where interfaces will be stored.
 */
val INTERFACES_DIRECTORY = ConfigurationProperty("INTERFACES_DIRECTORY", "interfaces", { it }, {
    if (it.isBlank()) throw IllegalArgumentException("interfaces directory must be defined")
})

/**
 * Delay in milliseconds between each key action repeat. May be necessary when game does not register a keypresses too well.
 */
val KEY_ACTION_DELAY = ConfigurationProperty("KEY_ACTION_DELAY", 15, String::toLong, {
    if (it !in (0..1_000)) throw IllegalArgumentException("key action delay must be in range from 0 to 1'000")
})

/**
 * How many times an action, e.g. keypress will be repeated. May be necessary when game does not register a single keypress too well.
 */
val KEY_ACTION_REPEATS = ConfigurationProperty("KEY_REPEATS", 2, String::toInt, {
    if (it !in (1..100)) throw IllegalArgumentException("key repeats must be in range from 1 to 100")
})

/**
 * Delay in milliseconds between each repeat. May be necessary when game does not register a keypresses too well.
 */
val KEY_ACTION_REPEATS_DELAY = ConfigurationProperty("KEY_ACTION_REPEATS_DELAY", 5, String::toLong, {
    if (it !in (0..1_000)) throw IllegalArgumentException("key repeats delay must be in range from 0 to 1'000")
})

data class ConfigurationProperty<T>(
    private val name: String,
    private val defaultValue: T,
    private val valueFromString: (String) -> T,
    private val validation: (T) -> Unit = {}
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