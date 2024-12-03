package com.leoapps.base.egg.domain.model

enum class EggBoilingType {
    SOFT,
    MEDIUM,
    HARD;

    companion object {
        fun fromString(s: String) = entries.firstOrNull { it.name == s }
    }
}