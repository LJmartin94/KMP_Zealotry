package features.Platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform