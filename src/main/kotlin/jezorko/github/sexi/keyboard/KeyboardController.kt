package jezorko.github.sexi.keyboard

import jezorko.github.sexi.shared.Controller
import jezorko.github.sexi.shared.ControllerRoute.Companion.post
import jezorko.github.sexi.shared.HttpResponse
import jezorko.github.sexi.shared.HttpResponse.Companion.noContent
import jezorko.github.sexi.shared.RoutingContextWrapper
import java.awt.Robot

class KeyboardController : Controller {

    private val robot = Robot()

    override fun routes() = listOf(
        post("/execute/:action", this::executeAction)
    )

    private fun executeAction(context: RoutingContextWrapper): HttpResponse {
        val actionAsString = context.getPathParam("action")
        val actionSequence = KeySequence.valueOf(actionAsString)

        synchronized(this) {
            actionSequence.keySequenceParts.forEach { keySequencePart ->
                keySequencePart.keyAction.robotAction(robot, keySequencePart.keyEvent)
            }
        }

        return noContent()
    }

}