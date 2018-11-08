package bucket.sdk.models.responses

import java.io.Serializable

data class RegisterTerminalResponse(
        val isApproved: Boolean,
        val apiKey: String,
        val retailerName: String,
        val retailerPhone: String,
        val address: Address,
        val errorCode: String?,
        val message: String?)
    : Serializable

data class Address(
        val address1: String?,
        val address2: String?,
        val address3: String?,
        val postalCode: String?,
        val city: String?,
        val state: String?)
    : Serializable