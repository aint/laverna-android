package com.github.android.lvrn.lvrnproject.view.util

import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
private val YEAR_MONTH_DAY_DOT_PATTERN = "dd.MM.yy HH:mm:ss"

fun convertMillisecondsToString(time: Long): String {
    val instant = Instant.ofEpochMilli(time)
    val zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)

    val formatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_DOT_PATTERN)
    return formatter.format(zdt)
}
