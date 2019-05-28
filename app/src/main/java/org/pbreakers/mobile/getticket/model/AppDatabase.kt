package org.pbreakers.mobile.getticket.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.pbreakers.mobile.getticket.model.dao.AgentDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import java.util.*

@TypeConverters(value = [AppDatabase.DateConverter::class])
@Database(entities = [Agence::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun agenceDao(): AgentDao

    class DateConverter {
        @TypeConverter
        fun timestampToDate(timestamp: Long): Date = Date(timestamp)

        @TypeConverter
        fun dateToTimestamp(date: Date): Long = date.time
    }
}