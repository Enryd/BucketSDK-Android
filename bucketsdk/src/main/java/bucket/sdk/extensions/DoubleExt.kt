package bucket.sdk.extensions

fun Double.format(digits: Int) = String.format("%.${digits}f", this)

/** Currency Parsers **/

fun Double.toWholeNumber() = toString().split(".").first().toInt()
