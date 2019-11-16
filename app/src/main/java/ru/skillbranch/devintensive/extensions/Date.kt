package ru.skillbranch.devintensive.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val YEAR = 365 * DAY

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String {
    val pattern = if(this.isSameDate(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.isSameDate(date: Date): Boolean {
    val day1 = this.time / DAY
    val day2 = date.time / DAY
    return day1 == day2
}
fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits(val  plEndings : Array<String>) {
    SECOND(arrayOf("секунду", "секунды", "секунд")),
    MINUTE(arrayOf("минуту", "минуты", "минут")),
    HOUR(arrayOf("час", "часа", "часов")),
    DAY(arrayOf("день", "дня", "дней"));

    fun plural(num: Int) : String {
        var modulo = num % 100
        if(modulo !in 11..14)
            modulo  = num % 10
        return when(modulo) {
            1     -> "$num ${plEndings[0]}"
            2,3,4 -> "$num ${plEndings[1]}"
            else  -> "$num ${plEndings[2]}"
        }
    }
}

fun Date.humanizeDiff(): String {
    val diff : Long = this.time - Date().time

    return ""
}
