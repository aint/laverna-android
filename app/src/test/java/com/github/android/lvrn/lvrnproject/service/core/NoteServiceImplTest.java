package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.valhallalabs.laverna.persistent.entity.Profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceImplTest {
    private final String profileId = "testprofileid";
    private final String title = "testtitle";
    private final String notebookId = "test";
    private final String tagId = "tagIdtest";
    private final boolean isTrash = false;
    private final String content = "my content test";
    private final String htmlContent = "my html content test";
    private final boolean isFavourite = true;
    private final String profileName = "profile name";
    private final String noteId = "noteid";
    private final String parentId = "parentid";
    private final String notebookName = "my notebook name";
    private final int counter = 10;
    private PaginationArgs paginationArgs;
    private Note note;
    private Notebook notebook;
    private NoteService noteService;
    private NoteForm noteForm;
    private Profile profile;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private TaskService taskService;
    @Mock
    private TagService tagService;
    @Mock
    private ProfileService profileService;
    @Mock
    private NotebookService notebookService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteServiceImpl(noteRepository, taskService, tagService, profileService, notebookService);
        noteForm = new NoteForm(profileId, isTrash, notebookId, title, content, htmlContent, isFavourite);
        note = new Note(noteId, profileId, isTrash, notebookId, title, System.currentTimeMillis(), System.currentTimeMillis(), content, htmlContent, isFavourite);
        notebook = new Notebook(notebookId, profileId, isTrash, parentId, notebookName, System.currentTimeMillis(), System.currentTimeMillis(), counter);
        profile = new Profile(profileId, profileName);
        paginationArgs = new PaginationArgs();

    }

    @Test
    public void create() {
        when(noteRepository.add(any())).thenReturn(true);
        when(profileService.getById(profileId)).thenReturn(Optional.of(profile));
        when(notebookService.getById(notebookId)).thenReturn(Optional.of(notebook));

        Optional<String> result = noteService.create(noteForm);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void update() {
        when(noteRepository.update(any())).thenReturn(true);
        when(noteRepository.getById(noteId)).thenReturn(Optional.of(note));
        when(notebookService.getById(notebookId)).thenReturn(Optional.of(notebook));

        boolean result = noteService.update(noteId, noteForm);
        assertThat(result).isTrue();
    }

    @Test
    public void getByTitle() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getByTitle(profileId, title, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getByTitle(profileId, title, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getTrashByTitle() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getTrashByTitle(profileId, title, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getTrashByTitle(profileId, title, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getByNotebook() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getByNotebook(notebookId, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getByNotebook(notebookId, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getFavourites() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getFavourites(profileId, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getFavourites(profileId, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getFavouritesByTitle() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getFavouritesByTitle(profileId, title, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getFavouritesByTitle(profileId, title, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getByTag() {
        final List<Note> expected = new ArrayList<>();
        when(noteRepository.getByTag(tagId, paginationArgs)).thenReturn(expected);

        List<Note> result = noteService.getByTag(tagId, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

}
