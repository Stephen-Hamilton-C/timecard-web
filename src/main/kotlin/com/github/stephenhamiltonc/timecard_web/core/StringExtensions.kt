fun String.pluralize(number: Number, plural = "$thiss") {
    return if(number != 1) {
        plural
    } else {
        this
    }
}
