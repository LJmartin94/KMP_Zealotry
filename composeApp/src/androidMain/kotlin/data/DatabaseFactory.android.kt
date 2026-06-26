package data

actual fun databaseFilePath(context: Any?): String {
    val ctx = context as android.content.Context
    return ctx.getDatabasePath("zealotry.db").absolutePath
}
