package com.leoapps.eggy.logs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leoapps.eggy.logs.domain.model.Log

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tag: String,
    val message: String,
    val timestamp: Long,
    val severity: LogSeverity
)

fun Log.toEntity() = LogEntity(
    tag = tag,
    message = message,
    timestamp = timestamp,
    severity = severity,
)

fun LogEntity.toDomain() = Log(
    tag = tag,
    message = message,
    timestamp = timestamp,
    severity = severity,
)
