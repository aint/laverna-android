package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class NoteViewModel : ViewModel() {

    private var _note = MutableLiveData<Note>()
    val note : LiveData<Note> = _note

    private var _notebook = MutableLiveData<Notebook>()
    val notebook : LiveData<Notebook> = _notebook


    fun setNote(note: Note) {
        this._note.value = note
    }

    fun setNotebook(notebook: Notebook) {
        this._notebook.value = notebook
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return NoteViewModel() as T
            }
        }
    }

}