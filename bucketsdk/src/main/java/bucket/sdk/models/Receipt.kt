package bucket.sdk.models

import android.app.Activity
import android.view.View
import bucket.sdk.R
import bucket.sdk.extensions.format
import bucket.sdk.extensions.toBitmap
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.receipt.view.*
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class Receipt(activity: Activity,
              date: String? = DEFAULT_DATE_FORMAT,
              storeName: String? = NA,
              address1: String? = NA,
              address2: String? = NA, phone: String? = NA,
              currency: String? = DEFAULT_CURRENCY,
              total: Double? = DEFAULT_MONETARY_VALUE,
              cash: Double? = DEFAULT_MONETARY_VALUE,
              change: Double? = DEFAULT_MONETARY_VALUE,
              bucket: Double? = DEFAULT_MONETARY_VALUE,
              website: String? = NA,
              showQRSection: Boolean = false,
              receiptCode: String? = "XXX-XXXX-XXX",
              qrCodeUrl: String? = null) {

    companion object {
        private const val DEFAULT_DATE_FORMAT = "MM/DD/YY hh:mm a"
        private const val NA = "N/A"
        private const val DEFAULT_CURRENCY = "$"
        private const val DEFAULT_MONETARY_VALUE = 0.0
    }

    private var layoutView : View = activity.layoutInflater.inflate(R.layout.receipt, null).apply {
        receipt_date.text = date
        receipt_store_name.text = storeName
        receipt_address1.text = address1
        receipt_address2.text = address2
        receipt_phone.text = phone
        receipt_total.text = "$currency${total?.format(2)}"
        receipt_cash.text = "$currency${cash?.format(2)}"
        receipt_change.text = "$currency${change?.format(2)}"
        receipt_bucket.text = "$currency${bucket?.format(2)}"
        receipt_website.text = website
        qr_section.apply { if (showQRSection) show() else hide() }
        receipt_code.text = receiptCode
        // generate qr and set
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            receipt_qr_code.setImageBitmap(bitmap)

        } catch (e: WriterException) {
            e.printStackTrace()
        }


    }

    fun createView() = layoutView

    fun createBitmap() = layoutView.toBitmap()

}