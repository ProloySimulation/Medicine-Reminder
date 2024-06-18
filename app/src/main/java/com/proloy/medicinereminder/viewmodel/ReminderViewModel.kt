package com.proloy.medicinereminder.viewmodel

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proloy.medicinereminder.database.OperationResult
import com.proloy.medicinereminder.database.MedicineDao
import com.proloy.medicinereminder.database.ReminderDao
import com.proloy.medicinereminder.database.data.Medicine
import com.proloy.medicinereminder.database.data.MedicineWithReminders
import com.proloy.medicinereminder.database.data.Reminder
import kotlinx.coroutines.launch
import java.util.*

class ReminderViewModel(
    private val medicineDao: MedicineDao,
    private val reminderDao: ReminderDao
) : ViewModel() {

    // Live Medicine Data
    val medicineName = MutableLiveData<String>()
    val medicineType = MutableLiveData<String>()
    val doseQuantity = MutableLiveData<String>()

    private val _showMorning = MutableLiveData<Boolean>(false)
    val showMorning: LiveData<Boolean> = _showMorning

    private val _showAfternoon = MutableLiveData<Boolean>(false)
    val showAfternoon: LiveData<Boolean> = _showAfternoon

    private val _showNight = MutableLiveData<Boolean>(false)
    val showNight: LiveData<Boolean> = _showNight

    private val _selectedMorningTime = MutableLiveData<String>()
    val selectedMorningTime: LiveData<String> = _selectedMorningTime

    private val _selectedAfternoonTime = MutableLiveData<String>()
    val selectedAfternoonTime: LiveData<String> = _selectedAfternoonTime

    private val _selectedNightTime = MutableLiveData<String>()
    val selectedNightTime: LiveData<String> = _selectedNightTime

    //Error handle
    private val _operationResult = MutableLiveData<OperationResult>()
    val operationResult: LiveData<OperationResult> = _operationResult

    fun saveMedicineWithReminders()
    {
        val name = medicineName.value ?: ""
        val type = medicineType.value ?: ""
        val dose = doseQuantity.value ?: ""

        viewModelScope.launch {
            try {
                val newMedicine = Medicine(name = name, type = type, dose = dose)
                val medicineId: Long = medicineDao.insertMedicine(newMedicine)

                _operationResult.value = OperationResult.Success

                if (_selectedMorningTime.value != null) {
                    val morningReminder = Reminder(
                        medicineId = medicineId,
                        timeSlot = "morning",
                        reminderTime = _selectedMorningTime.value!!
                    )
                    reminderDao.insertReminder(morningReminder)
                }

                if (_selectedAfternoonTime.value != null) {
                    val afternoonReminder = Reminder(
                        medicineId = medicineId,
                        timeSlot = "afternoon",
                        reminderTime = _selectedAfternoonTime.value!!
                    )
                    reminderDao.insertReminder(afternoonReminder)
                }

                if (_selectedNightTime.value != null) {
                    val nightReminder = Reminder(
                        medicineId = medicineId,
                        timeSlot = "night",
                        reminderTime = _selectedNightTime.value!!
                    )
                    reminderDao.insertReminder(nightReminder)
                }
            }
            catch (e: Exception){
                _operationResult.value = OperationResult.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun resetFields() {
        medicineName.value = ""
        medicineType.value = ""
        doseQuantity.value = ""
//        selectedMorningTime.value = ""
//        selectedAfternoonTime.value = ""
//        selectedNightTime.value = ""
    }

    fun onSlotSelected(position: Int) {
        when (position) {
            0 -> { // Three Times Daily
                _showMorning.value = true
                _showAfternoon.value = true
                _showNight.value = true
            }
            1 -> { // Two Times Daily
                _showMorning.value = true
                _showAfternoon.value = true
                _showNight.value = false
            }
            2 -> { // One Time Daily
                _showMorning.value = true
                _showAfternoon.value = false
                _showNight.value = false
            }
        }
    }

    fun showTimePicker(context: Context,timeSlot: String) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                when(timeSlot){
                    "morning" -> _selectedMorningTime.value = formattedTime
                    "afternoon" -> _selectedAfternoonTime.value = formattedTime
                    "night" -> _selectedNightTime.value = formattedTime
                }
            },
            hour,
            minute,
            true // true for 24-hour format
        )

        timePickerDialog.show()
    }

    fun getAllMedicines(): LiveData<List<Medicine>> {
        return medicineDao.getAllMedicines() // Use LiveData to get all medicines
    }

    fun getMedicineWithReminders(): LiveData<List<MedicineWithReminders>> {
        return medicineDao.getMedicineWithReminders() // Fetch combined data
    }
}