package bucket.sdk.v2.json.terminal

import java.io.Serializable

data class GetBillDenominationsResponse(
        val usesNaturalChangeFunction: Boolean? = false,
        val currencyDecimals: Int? = 2,
        val denominations: List<Double>)
    : Serializable
