package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class NoteViewModel : ViewModel() {

    private var note = MutableLiveData<Note>()
    private var notebook = MutableLiveData<Notebook>()

    fun getNote(): MutableLiveData<Note> {
        return note
    }

    fun setNote(note: Note) {
        this.note.value = note
    }

    fun getNotebook(): MutableLiveData<Notebook> {
        return notebook
    }

    fun setNotebook(notebook: Notebook) {
        this.notebook.value = notebook
    }

}