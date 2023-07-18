fun String.pluralize(number: Number, plural: String = "${this}s"): String {
    return if(number != 1) {
        plural
    } else {
        this
    }
}
