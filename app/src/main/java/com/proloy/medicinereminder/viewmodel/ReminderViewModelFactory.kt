package com.proloy.medicinereminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.proloy.medicinereminder.database.MedicineDao
import com.proloy.medicinereminder.database.ReminderDao

class ReminderViewModelFactory(
    private val medicineDao: MedicineDao,
    private val reminderDao: ReminderDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(medicineDao, reminderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T { // Retain compatibility with older patterns
        if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(medicineDao, reminderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}