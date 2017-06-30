package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListFragment;
import com.github.android.lvrn.lvrnproject.view.viewholder.TaskViewHolder;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
public class TasksListAdapter extends DataPostSetAdapter<Task, TaskViewHolder> {

    private TasksListFragment mTaskListFragment;
    private List<Task> mTasks;


    public TasksListAdapter(TasksListFragment tasksListFragment) {
        mTaskListFragment = tasksListFragment;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.tvTaskDescription.setText(mTasks.get(position).getDescription());
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
}
