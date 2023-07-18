import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

fun String.pluralize(number: Number, plural: String = "${this}s"): String {
    return if(number != 1) {
        plural
    } else {
        this
    }
}

fun String.downloadAsFile(type: String, fileName: String) {
    val blob = Blob(arrayOf(this), BlobPropertyBag(type))
    val anchorElement = document.createElement("a") as HTMLAnchorElement
    anchorElement.download = fileName
    anchorElement.href = URL.createObjectURL(blob)
    anchorElement.click()
}
