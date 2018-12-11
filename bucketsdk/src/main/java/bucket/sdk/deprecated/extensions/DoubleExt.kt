package bucket.sdk.deprecated.extensions

internal fun Double.format(digits: Int) = String.format("%.${digits}f", this)

/** Currency Parsers **/

internal fun Double.toWholeNumber() = toString().split(".").first().toInt()
