package com.proloy.medicinereminder.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.proloy.medicinereminder.database.data.Medicine
import com.proloy.medicinereminder.database.data.MedicineWithReminders

@Dao
interface MedicineDao {
    @Insert
    suspend fun insertMedicine(medicine: Medicine): Long

    @Query("SELECT * FROM Medicine")
    fun getAllMedicines(): LiveData<List<Medicine>>

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Transaction
    @Query(
        "SELECT * FROM Medicine " +
                "LEFT JOIN Reminder ON Medicine.medicineId = Reminder.medicineId"
    )
    fun getMedicineWithReminders(): LiveData<List<MedicineWithReminders>>
}