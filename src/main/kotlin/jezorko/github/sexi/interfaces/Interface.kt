package jezorko.github.sexi.interfaces

data class ElementConfiguration(
    val text: String?,
    val fontSize: String?,
    val action: String?
)

data class Interface(
    val templateName: String,
    val elementsConfiguration: Map<String, ElementConfiguration>
)