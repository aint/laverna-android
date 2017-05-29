package com.github.android.lvrn.lvrnproject.service.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.TrashDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;
import com.github.android.lvrn.lvrnproject.service.TrashDependedService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.TrashDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class TrashDependedServiceImpl <T1 extends TrashDependedEntity, T2 extends TrashDependedForm>
        extends ProfileDependedServiceImpl<T1, T2> implements TrashDependedService<T1, T2> {

    private final TrashDependedRepository<T1> mTrashDependedRepository;

    private final ProfileService mProfileService;

    public TrashDependedServiceImpl(@NonNull TrashDependedRepository<T1> trashDependedRepository, @NonNull ProfileService profileService) {
        super(trashDependedRepository, profileService);
        mTrashDependedRepository = trashDependedRepository;
        mProfileService = profileService;

    }

    @NonNull
    @Override
    public List<T1> getByProfile(@NonNull String profileId, boolean isTrash, @NonNull PaginationArgs paginationArgs) {
        return mTrashDependedRepository.getByProfile(profileId, isTrash, paginationArgs);
    }

    @Override
    public boolean moveToTrash(@NonNull String entityId) {
        return mTrashDependedRepository.moveToTrash(entityId);
    }

    @Override
    public boolean restoreFromTrash(@NonNull String entityId) {
        return mTrashDependedRepository.restoreFromTrash(entityId);
    }
}
