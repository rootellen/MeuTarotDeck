package br.edu.ifsp.scl.meutarotdeck.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TarotCard::class], version = 1)
abstract class TarotCardDatabase: RoomDatabase() {
    abstract fun tarotCardDAO(): TarotCardDAO

    companion object {
        @Volatile
        private var INSTANCE: TarotCardDatabase? = null

        fun getDatabase(context: Context): TarotCardDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarotCardDatabase::class.java,
                    "tarotcardroom.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}