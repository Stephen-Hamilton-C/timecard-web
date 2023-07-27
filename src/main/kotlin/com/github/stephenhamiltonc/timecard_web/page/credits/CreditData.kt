package com.github.stephenhamiltonc.timecard_web.page.credits

data class CreditData(
    val project: String,
    val author: String? = null,
    val license: String,
    val website: String
)
