package com.proloy.medicinereminder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proloy.medicinereminder.database.data.Medicine
import com.proloy.medicinereminder.database.data.Reminder

@Database(entities = [Medicine::class, Reminder::class], version = 2, exportSchema = false) // Check version and exportSchema
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao // Abstract method for Medicine DAO
    abstract fun reminderDao(): ReminderDao // Abstract method for Reminder DAO

    companion object{
        @Volatile
        private var INSTANCE: MedicineDatabase?= null
        fun getDatabase(context: Context): MedicineDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicineDatabase::class.java,
                    "medicine_database"
                ).fallbackToDestructiveMigration() // Handle migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}