package bucket.sdk.v2.json.terminal

import java.io.Serializable

data class RegisterTerminalBody(
        val terminalCode: String?)
    : Serializable