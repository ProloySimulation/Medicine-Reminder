package com.proloy.medicinereminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.proloy.medicinereminder.database.data.Reminder

@Dao
interface ReminderDao {
    @Insert
    suspend fun insertReminder(reminder: Reminder) // Insertion operation

    @Query("SELECT * FROM Reminder WHERE medicineId = :medicineId")
    suspend fun getRemindersByMedicine(medicineId: Int): List<Reminder> // Query operation

    @Delete
    suspend fun deleteReminder(reminder: Reminder) // Deletion operation
}