package com.leoapps.eggy.logs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tag: String,
    val message: String,
    val timestamp: Long,
    val severity: LogSeverity
)
