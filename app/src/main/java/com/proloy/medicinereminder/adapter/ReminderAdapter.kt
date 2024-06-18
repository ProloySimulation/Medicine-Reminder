package com.proloy.medicinereminder.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proloy.medicinereminder.database.data.Reminder
import com.proloy.medicinereminder.databinding.ItemReminderBinding

class ReminderAdapter(private val reminders: List<Reminder>):
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReminderBinding.inflate(inflater, parent, false)
        return ReminderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.bind(reminder)
    }

    override fun getItemCount() = reminders.size

    class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            Log.d("ReminderAdapter", "Binding reminder: $reminder")
            binding.reminder = reminder
            binding.executePendingBindings()
        }
    }
}