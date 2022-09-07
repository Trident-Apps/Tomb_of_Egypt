package au.net.ab.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Url::class], version = 1)
abstract class UrlDatabase : RoomDatabase() {

    abstract fun getDao(): UrlDao

    companion object {
        @Volatile
        private var instance: UrlDatabase? = null
        private val LOCK = Any()

        operator fun invoke(contex: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(contex).also { instance = it }
        }

        private fun buildDataBase(contex: Context) = Room.databaseBuilder(
            contex.applicationContext, UrlDatabase::class.java, "urldatabase"
        ).build()
    }

}