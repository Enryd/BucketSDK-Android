package bucket.sdk.v2.json.terminal

import java.io.Serializable

data class RegisterTerminalResponse(
        val isApproved: Boolean = false,
        val apiKey: String?,
        val retailerName: String?,
        val retailerPhone: String?,
        val requireEmployeeCode: Boolean?,
        val address: Address?)
    : Serializable

data class Address(
        val address1: String?,
        val address2: String?,
        val address3: String?,
        val postalCode: String?,
        val city: String?,
        val state: String?)
    : Serializable