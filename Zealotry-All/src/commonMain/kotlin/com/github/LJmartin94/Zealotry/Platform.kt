package com.github.LJmartin94.Zealotry

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform