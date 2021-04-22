package jezorko.github.sexi.templates

enum class ElementType { BUTTON, LABEL }

data class Position(val top: Int, val left: Int)
data class Size(val width: Int, val height: Int)
data class ColorScheme(val text: String = "black", val background: String = "white", val border: String = "black")

data class ElementTemplate(
    val id: String,
    val type: ElementType,
    val defaultAction: String?,
    val defaultText: String? = "",
    val font: String = "Courier New",
    val position: Position,
    val size: Size,
    val color: ColorScheme = ColorScheme("black", "white", "black")
)

data class LayoutTemplate(
    val importFonts: List<String> = emptyList(),
    val defaultFont: String = "Courier New",
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