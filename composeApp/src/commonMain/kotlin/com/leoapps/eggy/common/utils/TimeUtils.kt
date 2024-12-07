package com.leoapps.eggy.common.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

const val EMPTY_CALCULATED_TIME = "00:00"

enum class TimeFormatPattern {
    HH_MM_SS,
    MM_SS,
    MM_Min_SS_Sec,
}

fun Long.toFormattedTime(
    pattern: TimeFormatPattern = TimeFormatPattern.MM_SS
): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    return when (pattern) {
        TimeFormatPattern.HH_MM_SS -> {
            localDateTime.time.format(
                LocalTime.Format {
                    hour()
                    char(':')
                    minute()
                    char(':')
                    second()
                }
            )
        }

        TimeFormatPattern.MM_SS -> {
            localDateTime.time.format(
                LocalTime.Format {
                    minute()
                    char(':')
                    second()
                }
            )
        }

        TimeFormatPattern.MM_Min_SS_Sec -> {
            localDateTime.time.format(
                LocalTime.Format {
                    minute(padding = Padding.NONE)
                    chars(" min ")
                    second(padding = Padding.NONE)
                    chars(" sec")
                }
            )
        }
    }
}
