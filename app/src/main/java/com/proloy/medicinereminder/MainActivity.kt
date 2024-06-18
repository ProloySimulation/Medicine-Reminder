package com.proloy.medicinereminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.proloy.medicinereminder.adapter.MedicineAdapter
import com.proloy.medicinereminder.database.MedicineDatabase
import com.proloy.medicinereminder.database.OperationResult
import com.proloy.medicinereminder.databinding.ActivityMainBinding
import com.proloy.medicinereminder.viewmodel.ReminderViewModel
import com.proloy.medicinereminder.viewmodel.ReminderViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MedicineAdapter
    private lateinit var viewModel: ReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        // Get DAO instances from the database
        val medicineDao = MedicineDatabase.getDatabase(applicationContext).medicineDao()
        val reminderDao = MedicineDatabase.getDatabase(applicationContext).reminderDao()
        val factory = ReminderViewModelFactory(medicineDao, reminderDao)
        viewModel = ViewModelProvider(this, factory).get(ReminderViewModel::class.java)

        // Set ViewModel for Data Binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this,NewReminder::class.java))
        }

        viewModel.getMedicineWithReminders().observe(this, Observer { medicines->
            Log.e("Medicines", medicines.toString())
            adapter = MedicineAdapter(medicines)
            binding.recyclerView.adapter = adapter
            binding.emptyTextView.visibility = if (medicines.isEmpty()) View.VISIBLE else View.GONE
        })
    }
}