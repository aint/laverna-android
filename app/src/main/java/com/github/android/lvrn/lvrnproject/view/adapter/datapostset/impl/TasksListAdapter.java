package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemTaskBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListFragment;
import com.github.valhallalabs.laverna.persistent.entity.Task;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TaskViewHolder> implements DataPostSetAdapter<Task> {

    private TasksListFragment mTaskListFragment;
    private List<Task> mTasks;


    public TasksListAdapter(TasksListFragment tasksListFragment) {
        mTaskListFragment = tasksListFragment;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.itemTaskBinding.textViewTaskDescription.setText(mTasks.get(position).getDescription());
        holder.itemView.setOnClickListener(v -> mTaskListFragment.openRelatedNote(mTasks.get(position)));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public void setData(List<Task> data) {
        mTasks = data;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        ItemTaskBinding itemTaskBinding;

        TaskViewHolder(ItemTaskBinding itemTaskBinding) {
            super(itemTaskBinding.getRoot());
            this.itemTaskBinding = itemTaskBinding;
        }
    }
}
