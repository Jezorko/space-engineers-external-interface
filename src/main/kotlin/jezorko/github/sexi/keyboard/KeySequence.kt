package jezorko.github.sexi.keyboard

import jezorko.github.sexi.keyboard.KeyAction.PRESS
import jezorko.github.sexi.keyboard.KeyAction.RELEASE
import jezorko.github.sexi.shared.KEY_ACTION_REPEATS
import jezorko.github.sexi.shared.KEY_ACTION_REPEATS_DELAY
import java.awt.Robot
import java.awt.event.KeyEvent.*
import java.lang.Thread.sleep

private fun repeatAction(robotAction: (Robot, Int) -> Unit): (Robot, Int) -> Unit {
    if (KEY_ACTION_REPEATS.value == 1 && KEY_ACTION_REPEATS_DELAY.value == 0L) {
        return robotAction
    } else if (KEY_ACTION_REPEATS.value > 1 && KEY_ACTION_REPEATS_DELAY.value == 0L) {
        return { robot, keyCode -> repeat(KEY_ACTION_REPEATS.value) { robotAction(robot, keyCode) } }
    } else {
        return { robot, keyCode ->
            repeat(KEY_ACTION_REPEATS.value) {
                robotAction(robot, keyCode); sleep(KEY_ACTION_REPEATS_DELAY.value)
            }
        }
    }
}

enum class KeyAction(val robotAction: (Robot, Int) -> Unit) {
    PRESS(repeatAction(Robot::keyPress)),
    RELEASE(repeatAction(Robot::keyRelease))
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
    PRESS_KEY_9(click(VK_9)),

    PRESS_KEY_A(click(VK_A)),
    PRESS_KEY_B(click(VK_B)),
    PRESS_KEY_C(click(VK_C)),
    PRESS_KEY_D(click(VK_D)),
    PRESS_KEY_E(click(VK_E)),
    PRESS_KEY_F(click(VK_F)),
    PRESS_KEY_G(click(VK_G)),
    PRESS_KEY_H(click(VK_H)),
    PRESS_KEY_I(click(VK_I)),
    PRESS_KEY_J(click(VK_J)),
    PRESS_KEY_K(click(VK_K)),
    PRESS_KEY_L(click(VK_L)),
    PRESS_KEY_M(click(VK_M)),
    PRESS_KEY_N(click(VK_N)),
    PRESS_KEY_O(click(VK_O)),
    PRESS_KEY_P(click(VK_P)),
    PRESS_KEY_Q(click(VK_Q)),
    PRESS_KEY_R(click(VK_R)),
    PRESS_KEY_S(click(VK_S)),
    PRESS_KEY_T(click(VK_T)),
    PRESS_KEY_U(click(VK_U)),
    PRESS_KEY_V(click(VK_V)),
    PRESS_KEY_W(click(VK_W)),
    PRESS_KEY_X(click(VK_X)),
    PRESS_KEY_Y(click(VK_Y)),
    PRESS_KEY_Z(click(VK_Z))

}