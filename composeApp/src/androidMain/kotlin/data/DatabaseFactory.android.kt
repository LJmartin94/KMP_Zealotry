package data

import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(context: Any?): RoomDatabase.Builder<AppDatabase> {
    val appContext = (context as android.content.Context).applicationContext
    val dbFile = appContext.getDatabasePath("zealotry.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
