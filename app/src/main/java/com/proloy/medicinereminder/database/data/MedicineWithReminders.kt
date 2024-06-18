package com.proloy.medicinereminder.database.data

import androidx.room.Embedded
import androidx.room.Relation

data class MedicineWithReminders(
    @Embedded val medicine: Medicine,
    @Relation(
        parentColumn = "medicineId",
        entityColumn = "medicineId"
    )
    val reminders: List<Reminder>
)