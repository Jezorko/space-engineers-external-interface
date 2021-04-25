package jezorko.github.sexi.keyboard

import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.ControllerRoute.Companion.get
import jezorko.github.sexi.shared.ControllerRoute.Companion.post
import jezorko.github.sexi.shared.HttpResponse
import jezorko.github.sexi.shared.HttpResponse.Companion.badRequest
import jezorko.github.sexi.shared.HttpResponse.Companion.noContent
import jezorko.github.sexi.shared.HttpResponse.Companion.ok
import jezorko.github.sexi.shared.KEY_ACTION_DELAY
import jezorko.github.sexi.shared.RoutingContextWrapper
import java.awt.Robot
import java.lang.Thread.sleep

class KeyboardController : Controller {

    private val robot = Robot()

    override fun routes() = listOf(
        get("/actions", this::getActions),
        post("/actions/:action", this::executeAction)
    )

    private fun getActions(): HttpResponse {
        return ok(KeySequence.values())
    }

    private fun executeAction(context: RoutingContextWrapper): HttpResponse {
        val actionAsString = context.getPathParam("action")
        val actionSequence: KeySequence
        try {
            actionSequence = KeySequence.valueOf(actionAsString)
        } catch (invalidActionException: IllegalArgumentException) {
            return badRequest("invalid action: $actionAsString")
        }

        synchronized(this) {
            actionSequence.keySequenceParts.forEach { keySequencePart ->
                keySequencePart.keyAction.robotAction(robot, keySequencePart.keyEvent)
                if (KEY_ACTION_DELAY.value > 0) {
                    sleep(KEY_ACTION_DELAY.value)
                }
            }
        }

        return noContent()
    }

}