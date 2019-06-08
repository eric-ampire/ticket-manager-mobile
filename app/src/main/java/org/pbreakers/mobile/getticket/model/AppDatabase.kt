package org.pbreakers.mobile.getticket.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.pbreakers.mobile.getticket.model.dao.*
import org.pbreakers.mobile.getticket.model.entity.*
import java.util.*

@TypeConverters(value = [AppDatabase.DateConverter::class])
@Database(
    entities = [
        Agence::class,
        Etat::class,
        Bus::class,
        Lieu::class,
        Role::class,
        Utilisateur::class,
        Transit::class,
        Voyage::class,
        PointArret::class,
        Billet::class
    ],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun agenceDao(): AgenceDao
    abstract fun etatDao(): EtatDao
    abstract fun busDao(): BusDao
    abstract fun lieuDao(): LieuDao
    abstract fun roleDao(): RoleDao
    abstract fun pointArretDao(): PointArretDao
    abstract fun billetDao(): BilletDao
    abstract fun transitDao(): TransitDao
    abstract fun voyageDao(): VoyageDao
    abstract fun utilisateurDao(): UtilisateurDao

    class DateConverter {
        @TypeConverter
        fun timestampToDate(timestamp: Long): Date = Date(timestamp)

        @TypeConverter
        fun dateToTimestamp(date: Date): Long = date.time
    }
}