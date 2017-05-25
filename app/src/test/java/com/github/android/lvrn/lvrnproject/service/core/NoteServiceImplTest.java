package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TagRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TaskRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.NoteServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.NotebookServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.TagServiceImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.TaskServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NoteServiceImplTest {

    private Profile profile;

    private NoteService noteService;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        ProfileService profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
        profileService.create(new ProfileForm("Temp profile"));
        profile = profileService.getAll().get(0);
        profileService.closeConnection();

        noteService = new NoteServiceImpl(
                new NoteRepositoryImpl(),
                new TaskServiceImpl(new TaskRepositoryImpl(), profileService),
                new TagServiceImpl(new TagRepositoryImpl(), profileService),
                profileService,
                new NotebookServiceImpl(new NotebookRepositoryImpl(), profileService));

        noteService.openConnection();
    }

    @Test
    public void serviceShouldCreateNote() {
        assertThat(noteService.create(new NoteForm(profile.getId(), null, "Note Title", "Content", "Content", false))
                .isPresent())
                .isTrue();
    }

    @Test
    public void serviceShouldNotCreateNote() {
        assertThat(noteService.create(new NoteForm(profile.getId(), null, "", "Content", "Content", false))
                .isPresent())
                .isFalse();

        assertThat(noteService.create(new NoteForm(null, null, "hjkh", "Content", "Content", false))
                .isPresent())
                .isFalse();

        assertThat(noteService.create(new NoteForm(profile.getId(), "dfdfs", "hjkh", "Content", "Content", false))
                .isPresent())
                .isFalse();
    }

    @Test
    public void serviceShouldUpdateNote() {
        Optional<String> noteIdOptional = noteService.create(new NoteForm(profile.getId(), null, "Note Title", "Content", "Content", false));
        assertThat(noteIdOptional.isPresent()).isTrue();

        assertThat(noteService.update(noteIdOptional.get(), new NoteForm(null, null, "new Title", "new content", "new content", true)))
                .isTrue();
    }

    @Test
    public void serviceShouldAddTagsAndTasksToDatabase() {
        String content = "Content\n #my_first_tag #my_second tag\n well#it's_not_a_tag\n"
                + "[] what about task?\n" +
                "[X] of course\n"
                + "[]That's not a task";

        Optional<String> noteIdOptional = noteService.create(new NoteForm(profile.getId(), null, "Note Title", content, content, false));
        assertThat(noteIdOptional.isPresent()).isTrue();

        TagService tagService = new TagServiceImpl(new TagRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        tagService.openConnection();
        List<Tag> tags = tagService.getByNote(noteIdOptional.get());
        tagService.closeConnection();

        assertThat(tags).hasSize(2);

        TaskService taskService = new TaskServiceImpl(new TaskRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        taskService.openConnection();
        List<Task> tasks = taskService.getByNote(noteIdOptional.get());
        taskService.closeConnection();

        assertThat(tasks).hasSize(2);
    }

    @Test
    public void serviceShouldUpdateTagsAndTasksInDatabase() {
        String content1 = "Content\n #my_first_tag #my_second tag\n well#it's_not_a_tag\n"
                + "[] what about task?\n" +
                "[X] of course\n"
                + "[]That's not a task";

        Optional<String> noteIdOptional = noteService.create(new NoteForm(profile.getId(), null, "Note Title", content1, content1, false));
        assertThat(noteIdOptional.isPresent()).isTrue();

        String content2 = "Content\n #my_second tag_first_delted\n well#it''s_not_a_tag\n"
                + "#new_third_tag #and_new_fourth_tag\n"
                + "[X] of course\n"
                + "[]That''s not a task\n"
                + "[] new task, yeap\n"
                + "[X] another one";

        assertThat(noteService.update(noteIdOptional.get(), new NoteForm(profile.getId(), null, "new title", content2, content1, true)));

        TagService tagService = new TagServiceImpl(new TagRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        tagService.openConnection();
        List<Tag> tags = tagService.getByNote(noteIdOptional.get());
        tagService.closeConnection();

        assertThat(tags).hasSize(3);

        TaskService taskService = new TaskServiceImpl(new TaskRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        taskService.openConnection();
        List<Task> tasks = taskService.getByNote(noteIdOptional.get());
        taskService.closeConnection();

        assertThat(tasks).hasSize(3);
    }

    @Test
    public void serviceShouldGetEntityByTitle() {
        assertThat(noteService.create(new NoteForm(profile.getId(), null, "Note Title", "Content", "Content", false))
                .isPresent())
                .isTrue();

        assertThat(noteService.getByTitle(profile.getId(), "note", 1, 100)).hasSize(1);
    }

    @Test
    public void serviceShouldGetEntityByNotebook() {
        NotebookService notebookService = new NotebookServiceImpl(new NotebookRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        notebookService.openConnection();
        Optional<String> notebookIdOptional = notebookService.create(new NotebookForm(profile.getId(), null, "notebook"));
        notebookService.closeConnection();
        assertThat(notebookIdOptional.isPresent()).isTrue();
        noteService.create(new NoteForm(profile.getId(), notebookIdOptional.get(), "new note", "yeah", "yeah", false));

        assertThat(noteService.getByNotebook(notebookIdOptional.get(), 1, 100)).hasSize(1);
    }

    @Test
    public void serviceShouldGetEntityByTag() {
        TagService tagService = new TagServiceImpl(new TagRepositoryImpl(), new ProfileServiceImpl(new ProfileRepositoryImpl()));
        tagService.openConnection();
        Optional<String> tagIdOptional = tagService.create(new TagForm(profile.getId(), "#simple_tag"));
        assertThat(tagIdOptional.isPresent()).isTrue();


        noteService.create(new NoteForm(profile.getId(), null, "new note", "#simple_tag", "#simple_tag", false));

        assertThat(noteService.getByTag(tagIdOptional.get(), 1, 100)).hasSize(1);

        tagService.closeConnection();
    }

    @After
    public void finish() {
        noteService.closeConnection();
    }
}
