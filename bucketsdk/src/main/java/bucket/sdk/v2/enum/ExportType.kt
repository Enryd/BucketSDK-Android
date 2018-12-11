package bucket.sdk.v2.enum

import com.google.gson.annotations.SerializedName

enum class ExportType {
    @SerializedName("csv") CSV,
    @SerializedName("") DEFAULT
}