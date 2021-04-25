package jezorko.github.sexi.interfaces

import jezorko.github.sexi.keyboard.KeySequence

data class ElementConfiguration(
    val action: KeySequence
)

data class TabConfiguration(
    val elementsConfiguration: Map<String, ElementConfiguration>
)

data class Interface(
    val templateName: String,
    val tabsConfiguration: Map<Int, TabConfiguration>
)