package net.azarquiel.innocvjorgec.util

import java.text.SimpleDateFormat
import java.util.Locale
// Extension Function
fun String.formatDate(): String {
    val locale = Locale("es", "ES")
    var formatter = SimpleDateFormat("yyyy-MM-dd", locale)
    val date = formatter.parse(this)
    date?.let {
        formatter = SimpleDateFormat("dd-MM-yyyy", locale)
        return formatter.format(it)
    }
    return ""
}
fun String.formatDateRev(): String {
    val locale = Locale("es", "ES")
    var formatter = SimpleDateFormat("dd-MM-yyyy", locale)
    val date = formatter.parse(this)
    date?.let {
        formatter = SimpleDateFormat("yyyy-MM-dd", locale)
        return formatter.format(it)
    }
    return ""
}