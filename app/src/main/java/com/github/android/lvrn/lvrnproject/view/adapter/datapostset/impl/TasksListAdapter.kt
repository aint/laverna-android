package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemTaskBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListFragment
import com.github.valhallalabs.laverna.persistent.entity.Task

class TasksListAdapter(private val mTaskListFragment: TasksListFragment) :
    RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>(), DataPostSetAdapter<Task> {

    private var mTasks: List<Task>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemTaskBinding.textViewTaskDescription.setText(mTasks!![position].description)
        holder.itemView.setOnClickListener { v: View? ->
            mTaskListFragment.openRelatedNote(
                mTasks!![position]
            )
        }
    }

    override fun getItemCount(): Int {
        return mTasks!!.size
    }

    override fun setData(data: List<Task>) {
        mTasks = data
    }

    class TaskViewHolder(itemTaskBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemTaskBinding.getRoot()) {
        var itemTaskBinding: ItemTaskBinding = itemTaskBinding
    }

}