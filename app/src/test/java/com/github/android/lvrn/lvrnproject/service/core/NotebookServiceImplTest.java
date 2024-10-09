package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.service.core.impl.NotebookServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(MockitoJUnitRunner.class)
public class NotebookServiceImplTest {
    private NotebookService notebookService;
    private final String id = "testid";
    private final String profileId = "testprofile";
    private final String parentId = "parentid";
    private final String notebookId = "notebookId";
    private final boolean isTrash = true;
    private final String name = "name";
    @Mock
    private NotebookRepository notebookRepository;
    @Mock
    private ProfileService profileService;
    private NotebookForm notebookForm;
    private Notebook notebook;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notebookService = new NotebookServiceImpl(notebookRepository, profileService);
        notebookForm = new NotebookForm(profileId, isTrash, parentId, name);
        notebook = new Notebook(notebookId, profileId, isTrash, parentId, name, System.currentTimeMillis(), System.currentTimeMillis(), 0);
    }

    @Test
    public void getByName() throws Exception {
        final PaginationArgs paginationArgs = new PaginationArgs();
        final List<Notebook> expected = new ArrayList<>();
        when(notebookRepository.getByName(profileId, name, paginationArgs)).thenReturn(expected);

        List<Notebook> result = notebookService.getByName(profileId, name, paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void create() throws Exception {
        when(notebookRepository.add(any())).thenReturn(true);
        when(notebookRepository.getById(anyString())).thenReturn(Optional.of(notebook));
        when(profileService.getById(profileId)).thenReturn(Optional.of(new Profile(profileId, name)));

        Optional<String> result = notebookService.create(notebookForm);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void update() throws Exception {
        when(notebookRepository.update(any())).thenReturn(true);
        when(notebookRepository.getById(anyString())).thenReturn(Optional.of(notebook));

        boolean result = notebookService.update(id, notebookForm);
        assertThat(result).isTrue();

    }
}
