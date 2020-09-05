package ru.skillbranch.devintensive.extensions

import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = java.text.SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int) = "$value ${
            when (value) {
                1 -> "секунду"
                in 2..4 -> "секунды"
                else -> "секунд"
            }
        }"
    },
    MINUTE {
        override fun plural(value: Int) = "$value ${
            when (value) {
                1 -> "минуту"
                in 2..4 -> "минуты"
                else -> "минут"
            }
        }"
    },
    HOUR {
        override fun plural(value: Int) = "$value ${
            when (value) {
                1 -> "час"
                in 2..4 -> "часа"
                else -> "часов"
            }
        }"
    },
    DAY {
        override fun plural(value: Int) = "$value ${
            when (value) {
                1 -> "день"
                in 2..4 -> "дня"
                else -> "дней"
            }
        }"
    };

    abstract fun plural(value: Int): String
}

//Реализуй extension Date.humanizeDiff(date) (значение по умолчанию текущий момент времени) для форматирования вывода разницы между датами в человекообразном формате, с учетом склонения числительных. Временные интервалы преобразований к человекообразному формату доступны в ресурсах к заданию
//Пример:
//Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
//Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
//Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
//Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
//Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
//Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
//0с - 1с "только что"
//1с - 45с "несколько секунд назад"
//45с - 75с "минуту назад"
//75с - 45мин "N минут назад"
//45мин - 75мин "час назад"
//75мин 22ч "N часов назад"
//22ч - 26ч "день назад"
//26ч - 360д "N дней назад"
//>360д "более года назад"
fun Date.humanizeDiff(date: Date = Date()): String {
    val past = date.compareTo(this) == 1
    return when (val diff = (time - date.time).absoluteValue) {
        in 0..1 * SECOND -> "только что"
        in 1 * SECOND..45 * SECOND -> if (past) "несколько секунд назад" else "через несколько секунд"
        in 45 * SECOND..75 * SECOND -> if (past) "минуту назад" else "через минуту"
        in 75 * SECOND..45 * MINUTE -> {
            val dTime = diff / MINUTE
            val units = when (dTime) {
                1L -> "минуту"
                in 2..4 -> "минуты"
                else -> "минут"
            }
            //if (past) "$dTime $units назад" else "через $dTime $units"
            val str = TimeUnits.MINUTE.plural((diff / MINUTE).toInt())
            if (past) "$str назад" else "через $str"
        }
        in 45 * MINUTE..75 * MINUTE -> if (past) "час назад" else "через час"
        in 75 * MINUTE..22 * HOUR -> {
            val dTime = diff / HOUR
            val units = when (dTime) {
                1L -> "час"
                in 2..4 -> "часа"
                else -> "часов"
            }
            if (past) "$dTime $units назад" else "через $dTime $units"
        }
        in 22 * HOUR..26 * HOUR -> if (past) "день назад" else "через день"
        in 26 * HOUR..360 * DAY -> {
            val dTime = diff / DAY
            val units = when (dTime) {
                1L -> "день"
                in 2..4 -> "дня"
                else -> "дней"
            }
            if (past) "$dTime $units назад" else "через $dTime $units"
        }
        else -> if (past) "более года назад" else "более чем через год"
    }
}