package com.github.android.lvrn.lvrnproject.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.android.lvrn.lvrnproject.view.ViewModelFactory
import com.github.android.lvrn.lvrnproject.view.ViewModelKey
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteViewModel
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindNoteViewModel(viewModel: NoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteEditorViewModel::class)
    abstract fun bindNoteEditorViewModel(vIewModel: NoteEditorViewModel) : ViewModel
}