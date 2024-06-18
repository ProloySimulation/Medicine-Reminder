package com.proloy.medicinereminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.proloy.medicinereminder.database.MedicineDatabase
import com.proloy.medicinereminder.database.OperationResult
import com.proloy.medicinereminder.databinding.ActivityMainBinding
import com.proloy.medicinereminder.databinding.ActivityNewReminderBinding
import com.proloy.medicinereminder.viewmodel.ReminderViewModel
import com.proloy.medicinereminder.viewmodel.ReminderViewModelFactory

class NewReminder : AppCompatActivity() {

    private lateinit var binding: ActivityNewReminderBinding
    private lateinit var viewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reminder)
        binding = ActivityNewReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val medicineDao = MedicineDatabase.getDatabase(applicationContext).medicineDao()
        val reminderDao = MedicineDatabase.getDatabase(applicationContext).reminderDao()
        val factory = ReminderViewModelFactory(medicineDao, reminderDao)
        viewModel = ViewModelProvider(this, factory).get(ReminderViewModel::class.java)


        // Set ViewModel for Data Binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.operationResult.observe(this, Observer { result ->
            when (result) {
                is OperationResult.Success -> {
                    // Show success message
                    Toast.makeText(this, "Medicine saved successfully", Toast.LENGTH_SHORT).show()
                    viewModel.resetFields()
                }
                is OperationResult.Error -> {
                    // Show error message with details
                    Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        setupAutoCompleteTextViewAdapter(binding.autoCompleteTextViewType,R.array.spinner_type)
        setupAutoCompleteTextViewAdapter(binding.autoCompleteTimeSlots,R.array.spinner_slots)
        setupAutoCompleteTextViewAdapter(binding.autoCompleteTextViewSlotOne,R.array.spinner_day)
        setupAutoCompleteTextViewAdapter(binding.autoCompleteTextViewSlotTwo,R.array.spinner_day)
        setupAutoCompleteTextViewAdapter(binding.autoCompleteTextViewSlotThree,R.array.spinner_day)
    }

    fun setupAutoCompleteTextViewAdapter(autoCompleteTextView: AutoCompleteTextView, itemsArrayId: Int) {
        val adapter = ArrayAdapter.createFromResource(
            autoCompleteTextView.context,
            itemsArrayId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        autoCompleteTextView.setAdapter(adapter)
    }
}