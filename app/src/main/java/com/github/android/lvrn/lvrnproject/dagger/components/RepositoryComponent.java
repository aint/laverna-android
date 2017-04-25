package com.github.android.lvrn.lvrnproject.dagger.components;

import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Singleton
@Component(modules = RepositoryModule.class)
public interface RepositoryComponent {

    void injectProfilesService(ProfilesService profilesService);

    void injectNotesService(NotesService notesService);
}

