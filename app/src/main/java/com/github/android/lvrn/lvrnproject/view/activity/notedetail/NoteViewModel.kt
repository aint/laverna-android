package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

}