package com.proloy.medicinereminder.database.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Medicine::class,
        parentColumns = ["medicineId"],
        childColumns = ["medicineId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderId: Int = 0,
    val medicineId: Long,
    val timeSlot: String,
    val reminderTime: String
)
