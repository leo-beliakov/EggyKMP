package com.leoapps.eggy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform