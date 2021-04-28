package com.github.android.lvrn.lvrnproject.service.core;

import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.google.common.base.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceImplTest {
    private static String profileName = "testprofilename";
    private static String profileId = "profileId";
    @Mock
    ProfileRepository profileRepository;
    private ProfileService profileService;
    private ProfileForm profileForm;
    private Profile profile;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        profileService = new ProfileServiceImpl(profileRepository);
        profileForm = new ProfileForm(profileName);
        profile = new Profile(profileId,profileForm.getName());
    }

    @Test
    public void getAll(){
        List<Profile> expected = new ArrayList<>();
        when(profileRepository.getAll()).thenReturn(expected);

        List<Profile> result = profileService.getAll();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void create(){
        when(profileRepository.add(any())).thenReturn(true);

        Optional<String> result = profileService.create(profileForm);
        assertThat(result.isPresent()).isTrue();
    }
}
