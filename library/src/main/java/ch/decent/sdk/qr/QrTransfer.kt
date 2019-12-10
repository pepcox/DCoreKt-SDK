package ch.decent.sdk.qr

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

private const val DCT_QR_PREFIX = "decent:"
private const val ASSET = "asset"
private const val AMOUNT = "amount"
private const val MEMO = "memo"

data class QrTransfer(
    val accountName: String,
    val assetSymbol: String = "",
    val amount: String = "",
    val memo: String = ""
)

fun QrTransfer.asQrContent(): String =
    "$DCT_QR_PREFIX${this.accountName}?" +
        "$ASSET=${this.assetSymbol.urlEncode()}&" +
        "$AMOUNT=${this.amount.urlEncode()}&" +
        "$MEMO=${this.memo.urlEncode()}"

fun String.asWalletReceiveQrInput(): QrTransfer =
    if (this.startsWith(DCT_QR_PREFIX, true)) {
      val matcher = Pattern.compile("($AMOUNT|$ASSET|$MEMO)=([^&]*)").matcher(this.urlDecode())
      val params = HashMap<String, String>()
      while (matcher.find()) {
        params[matcher.group(1)] = matcher.group(2)
      }
      QrTransfer(
          this.split("?").firstOrNull()?.replace(DCT_QR_PREFIX, "") ?: "",
          params[ASSET] ?: "",
          params[AMOUNT] ?: "",
          params[MEMO] ?: ""
      )
    } else {
      QrTransfer(this)
    }

private fun String.urlEncode() = URLEncoder.encode(this, StandardCharsets.UTF_8.toString()).replace("+", "%20")
private fun String.urlDecode() = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
