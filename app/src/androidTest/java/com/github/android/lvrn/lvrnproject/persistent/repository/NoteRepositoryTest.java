package com.github.android.lvrn.lvrnproject.persistent.repository;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.valhallalabs.laverna.persistent.entity.Tag;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class NoteRepositoryTest {

    private NoteRepository noteRepository;

    private Note note1;

    private Note note2;

    private Note note3;

    private Notebook notebook;

    private Profile profile;

    private List<Note> notes;

    @Before
    public void setUp() {
        DatabaseManager.Companion.initializeInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());

        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();


        noteRepository = new NoteRepositoryImpl();

        notebook = new Notebook("notebook_id_1", "profile_id_1", false, null, "notebook1", 1111, 2222, 0);
        NotebookRepositoryImpl notebooksRepository = new NotebookRepositoryImpl();
        notebooksRepository.openDatabaseConnection();
        notebooksRepository.add(notebook);
        notebooksRepository.closeDatabaseConnection();

        note1 = new Note(
                "note_id_1",
                "profile_id_1",
                false,
                "notebook_id_1",
                "title_1",
                1111,
                2222,
                "content1",
                "content1",
                false
        );

        note2 = new Note(
                "note_id_2",
                "profile_id_1",
                false,
                "notebook_id_1",
                "title_2",
                1111,
                2222,
                "content2",
                "content2",
                false
        );

        note3 = new Note(
                "note_id_3",
                "profile_id_2",
                false,
                "notebook_id_2",
                "title_3",
                1111,
                2222,
                "content3",
                "content3",
                false
        );

        notes = new ArrayList<>();
        notes.addAll(Arrays.asList(note1, note2, note3));
        noteRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        noteRepository.add(note1);
        Optional<Note> noteOptional = noteRepository.getById(note1.getId());
        assertThat(noteOptional.isPresent()).isTrue();
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        List<Note> noteEntities1 = noteRepository
                .getByProfile(profile.getId(), new PaginationArgs());

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldGetEntitiesByNotebookId() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        List<Note> noteEntities1 = noteRepository
                .getByNotebook(notebook.getId(), new PaginationArgs());

        assertThat(noteEntities1.size()).isNotEqualTo(notes.size());
        assertThat(noteEntities1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) noteEntities1).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldGetNotesByTagId() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagRepositoryImpl tagsRepository = new TagRepositoryImpl();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();

        noteRepository.add(note1);
        noteRepository.add(note2);

        noteRepository.addTagToNote(note1.getId(), tag.getId());
        noteRepository.addTagToNote(note2.getId(), tag.getId());

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), new PaginationArgs());

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 1);

        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldRemoveTagsOfNote() {
        Tag tag = new Tag("tag_id_1", "profile_id_1", "tag1", 1111, 2222, 0);
        TagRepositoryImpl tagsRepository = new TagRepositoryImpl();
        tagsRepository.openDatabaseConnection();
        tagsRepository.add(tag);
        tagsRepository.closeDatabaseConnection();


        noteRepository.add(note1);
        noteRepository.add(note2);

        noteRepository.addTagToNote(note1.getId(), tag.getId());
        noteRepository.addTagToNote(note2.getId(), tag.getId());

        noteRepository.removeTagFromNote(note1.getId(), tag.getId());

        List<Note> notes1 = noteRepository.getByTag(tag.getId(), new PaginationArgs());

        assertThat(notes1.size()).isNotEqualTo(notes.size());
        assertThat(notes1.size()).isEqualTo(notes.size() - 2);

        notes.remove(note1);
        notes.remove(note3);
        assertThat((Object) notes1).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        noteRepository.add(note1);

        note1.setTitle("new title");

        noteRepository.update(note1);

        Optional<Note> noteOptional = noteRepository.getById(note1.getId());
        assertThat(noteOptional.get()).isEqualToComparingFieldByField(note1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        noteRepository.add(note1);

        noteRepository.remove(note1.getId());

        assertThat(noteRepository.getById(note1.getId()).isPresent()).isFalse();
    }

    @Test
    public void repositoryShouldGetNoteByName() {
        noteRepository.add(note1);
        note2.setTitle("title2");
        noteRepository.add(note2);

        List<Note> result1 = noteRepository.getByTitle(profile.getId(), "title", new PaginationArgs());

        assertThat(result1).hasSize(2);

        List<Note> result2 = noteRepository.getByTitle(profile.getId(), "title_1", new PaginationArgs());

        assertThat(result2).hasSize(1);
    }

    @Test
    public void repositoryShouldMoveToTrashEntity() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.moveToTrash(note1.getId());
        noteRepository.moveToTrash(note2.getId());
        List<Note> notesListExpectedInTrash = noteRepository.getTrashByProfile(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedInTrash).isNotNull();
        assertThat(notesListExpectedInTrash.size()).isEqualTo(2);
        assertThat(notesListExpectedInTrash.get(0).isTrash()).isEqualTo(true);

        notes.remove(note3);
        assertThat((Object) notesListExpectedInTrash).isEqualToComparingFieldByField(notes);
    }

    @Test
    public void repositoryShouldRestoreEntityFromTrash() {
        note1.setTrash(true);
        note2.setTrash(true);
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.restoreFromTrash(note1.getId());
        List<Note> notesListExpectedInTrash = noteRepository.getTrashByProfile(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedInTrash).isNotNull();
        assertThat(notesListExpectedInTrash.size()).isEqualTo(1);
    }

    @Test
    public void repositoryShouldRemoveForeverEntity() {
        note1.setTrash(true);
        note2.setTrash(true);
        note3.setTrash(true);
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);
        noteRepository.removeForPermanent(note1.getId());
        noteRepository.removeForPermanent(note3.getId());

        List<Note> notesListExpectedInTrash = noteRepository.getTrashByProfile(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedInTrash).isNotNull();
        assertThat(notesListExpectedInTrash.size()).isEqualTo(1);

        List<Note> notesListExpectedByProfile = noteRepository.getByProfile(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedByProfile).isNotNull();
        assertThat(notesListExpectedByProfile.size()).isEqualTo(0);
    }

    @Test
    public void repositoryShouldSetNoteAsFavourite() {
        noteRepository.add(note1);
        noteRepository.add(note2);
        noteRepository.add(note3);

        noteRepository.changeNoteFavouriteStatus(note1.getId(), true);
        noteRepository.changeNoteFavouriteStatus(note2.getId(), true);

        List<Note> notesListExpectedFavourites = noteRepository.getFavourites(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedFavourites).isNotNull();
        assertThat(notesListExpectedFavourites.size()).isEqualTo(2);

        noteRepository.changeNoteFavouriteStatus(note1.getId(), false);
        List<Note> notesListExpectedFavouritesUpdated = noteRepository.getFavourites(profile.getId(), new PaginationArgs());
        assertThat(notesListExpectedFavouritesUpdated).isNotNull();
        assertThat(notesListExpectedFavouritesUpdated.size()).isEqualTo(1);
    }

    @After
    public void finish() {
        noteRepository.closeDatabaseConnection();
        DatabaseManager.Companion.getInstance().removeInstance();
    }
}
