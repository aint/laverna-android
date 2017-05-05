package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.BasicService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface ProfileService extends BasicService<Profile, ProfileForm> {

    @NonNull
    List<Profile> getAll();
}
