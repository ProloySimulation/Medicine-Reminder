package com.proloy.medicinereminder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proloy.medicinereminder.database.data.Medicine
import com.proloy.medicinereminder.database.data.MedicineWithReminders
import com.proloy.medicinereminder.databinding.ItemMedicineBinding

class MedicineAdapter(private val items: List<MedicineWithReminders>) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMedicineBinding.inflate(inflater, parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = items[position]
        holder.bind(medicine)
    }

    override fun getItemCount() = items.size

    class MedicineViewHolder(private val binding: ItemMedicineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medicineWithReminders: MedicineWithReminders) {
            binding.medicineWithReminders = medicineWithReminders

            // Set up RecyclerView for reminders
            val reminderAdapter = ReminderAdapter(medicineWithReminders.reminders)
            binding.remindersRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = reminderAdapter
            }

            binding.executePendingBindings()
        }
    }
}