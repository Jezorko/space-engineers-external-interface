package jezorko.github.sexi.templates

enum class ElementType { BUTTON, LABEL }

data class Position(val top: Int, val left: Int) {
    init {
        if (top !in (0..100) || left !in (0..100)) {
            throw IllegalArgumentException("position elements must have value from 0 to 100")
        }
    }
}

data class Size(val width: Int, val height: Int) {
    init {
        if (width !in (0..100) || height !in (0..100)) {
            throw IllegalArgumentException("size elements must have value from 0 to 100")
        }
    }
}

data class ColorScheme(val text: String = "black", val background: String = "white", val border: String = "black")

data class Border(val style: String = "solid", val size: String = "initial")

data class ElementTemplate(
    val id: String,
    val type: ElementType,
    val defaultAction: String?,
    val defaultText: String? = "",
    val font: String?,
    val fontSize: String = "initial",
    val position: Position,
    val size: Size,
    val color: ColorScheme = ColorScheme(),
    val border: Border = Border()
)

private val whiteSpaceRegex = Regex("\\s")

data class LayoutTemplate(
    val importFonts: List<String> = emptyList(),
    val defaultFont: String = "Courier New",
    val backgroundColor: String = "white",
    val tabs: Map<Int, List<ElementTemplate>>
) {
    init {
        tabs.keys.forEach { tabId ->
            if (tabId !in (0..9)) {
                throw IllegalArgumentException("invalid tab ID: $tabId, must be <0, 9>")
            }
        }

        val allIds = tabs.values.flatten()
            .map { element -> element.id }
            .onEach { elementId ->
                if (elementId.contains(whiteSpaceRegex)) {
                    throw IllegalArgumentException("element ID must not contain whitespaces")
                }
            }

        if (allIds.size != allIds.distinct().size) {
            val duplicateIdsCount = allIds.groupingBy { it }.eachCount().filter { it.value > 1 }
            throw IllegalArgumentException(
                "tab element IDs must be unique; duplicate IDs: " +
                        duplicateIdsCount.map { '"' + it.key + "\" occurring " + it.value + " times" }
                            .joinToString(", ")
            )
        }
    }
}