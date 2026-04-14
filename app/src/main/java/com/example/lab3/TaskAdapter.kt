package com.example.lab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task>,
    private val onDeleteClick: (Int) -> Unit,
    private val onToggleClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTask: TextView = view.findViewById(R.id.tvTask)
        val cbDone: CheckBox = view.findViewById(R.id.cbDone)
        val tvPriority: TextView = view.findViewById(R.id.tvPriority)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTask.text = task.title
        holder.cbDone.isChecked = task.isDone

        holder.tvPriority.text = when (task.priority) {
            1 -> "Приоритет: 1"
            2 -> "Приоритет: 2"
            3 -> "Приоритет: 3"
            else -> "Приоритет: неизвестно"
        }

        holder.cbDone.setOnClickListener {
            onToggleClick(holder.adapterPosition)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateList(newList: List<Task>) {
        val diffCallback = TaskDiffCallback(tasks, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        tasks = newList
        diffResult.dispatchUpdatesTo(this)
    }
}