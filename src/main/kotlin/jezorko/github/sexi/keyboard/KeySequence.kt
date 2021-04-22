package jezorko.github.sexi.keyboard

import jezorko.github.sexi.keyboard.KeyAction.PRESS
import jezorko.github.sexi.keyboard.KeyAction.RELEASE
import java.awt.event.KeyEvent.*

enum class KeyAction {
    PRESS, RELEASE
}

data class KeySequencePart(val keyAction: KeyAction, val keyEvent: Int)

fun click(key: Int): Array<KeySequencePart> = arrayOf(
    KeySequencePart(PRESS, key),
    KeySequencePart(RELEASE, key)
)

fun multiClick(vararg key: Int): Array<KeySequencePart> =
    (key.map { KeySequencePart(PRESS, it) }
            + key.reversed().map { KeySequencePart(RELEASE, it) }).toTypedArray()

enum class KeySequence(val keySequenceParts: Array<KeySequencePart>) {

    OPEN_TAB_0(multiClick(VK_CONTROL, VK_0)),
    OPEN_TAB_1(multiClick(VK_CONTROL, VK_1)),
    OPEN_TAB_2(multiClick(VK_CONTROL, VK_2)),
    OPEN_TAB_3(multiClick(VK_CONTROL, VK_3)),
    OPEN_TAB_4(multiClick(VK_CONTROL, VK_4)),
    OPEN_TAB_5(multiClick(VK_CONTROL, VK_5)),
    OPEN_TAB_6(multiClick(VK_CONTROL, VK_6)),
    OPEN_TAB_7(multiClick(VK_CONTROL, VK_7)),
    OPEN_TAB_8(multiClick(VK_CONTROL, VK_8)),
    OPEN_TAB_9(multiClick(VK_CONTROL, VK_9)),

    PRESS_KEY_0(click(VK_0)),
    PRESS_KEY_1(click(VK_1)),
    PRESS_KEY_2(click(VK_2)),
    PRESS_KEY_3(click(VK_3)),
    PRESS_KEY_4(click(VK_4)),
    PRESS_KEY_5(click(VK_5)),
    PRESS_KEY_6(click(VK_6)),
    PRESS_KEY_7(click(VK_7)),
    PRESS_KEY_8(click(VK_8)),
    PRESS_KEY_9(click(VK_9))

}