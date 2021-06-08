package com.maryang.testsample.data.db

import androidx.room.*
import com.maryang.testsample.base.BaseApplication
import com.maryang.testsample.data.source.GithubDao
import com.maryang.testsample.entity.GithubRepo
import java.util.*


@Database(
    entities = [GithubRepo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun get(): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        BaseApplication.appContext, AppDatabase::class.java, "app_db.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
