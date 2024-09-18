package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass) as T
    }

}