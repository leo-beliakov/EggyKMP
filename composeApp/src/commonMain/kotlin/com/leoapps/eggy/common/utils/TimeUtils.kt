package com.leoapps.eggy.common.utils

const val EMPTY_CALCULATED_TIME = "00:00"

private const val MILLIS_IN_SECOND = 1000
private const val SECONDS_IN_MINUTE = 60
private const val MINUTES_IN_HOUR = 60
private const val TIME_FORMAT = "%02d:%02d"

fun convertMsToTimerText(ms: Long): String {
    val totalSeconds = ms / MILLIS_IN_SECOND
    val minutes = (totalSeconds / SECONDS_IN_MINUTE) % MINUTES_IN_HOUR
    val seconds = totalSeconds % SECONDS_IN_MINUTE
    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    return "$formattedMinutes:$formattedSeconds"
}

fun convertMsToText(ms: Long): String {
    val seconds = ms / MILLIS_IN_SECOND
    val minutes = seconds / SECONDS_IN_MINUTE
    val remainingSeconds = seconds % SECONDS_IN_MINUTE
    return "$minutes min $remainingSeconds sec" //todo use resources
}