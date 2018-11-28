package bucket.sdk.v2.json.transaction

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.Serializable

data class CreateTransactionResponse(
        val customerCode: String?,
        val qrCodeContent: String?,
        val bucketTransactionId: Int?,
        val amount: Double?,
        val locationId: String?,
        val clientTransactionId: String?)
    : Serializable {

    val qrCodeImage: Bitmap? get() {
        val multiFormatWriter = MultiFormatWriter()
        var bitmap: Bitmap? = null
        try {
            val bitMatrix = multiFormatWriter.encode(qrCodeContent.toString(), BarcodeFormat.QR_CODE, 200, 200)
            bitmap = BarcodeEncoder().createBitmap(bitMatrix)

        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

}