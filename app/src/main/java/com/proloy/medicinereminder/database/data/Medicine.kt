package com.proloy.medicinereminder.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medicine(
    @PrimaryKey(autoGenerate = true) val medicineId: Int = 0,
    val name: String,
    val type: String,
    val dose: String
)
