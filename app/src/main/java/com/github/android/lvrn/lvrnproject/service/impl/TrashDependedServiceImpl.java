package com.github.android.lvrn.lvrnproject.service.impl;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;
import com.github.android.lvrn.lvrnproject.service.TrashDependedService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.TrashDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class TrashDependedServiceImpl<T1 extends TrashDependedEntity, T2 extends TrashDependedForm<?>>
        extends ProfileDependedServiceImpl<T1, T2> implements TrashDependedService<T1, T2> {

    private final TrashDependedRepository<T1> mTrashDependedRepository;

    public TrashDependedServiceImpl(@NonNull TrashDependedRepository<T1> trashDependedRepository, @NonNull ProfileService profileService) {
        super(trashDependedRepository, profileService);
        mTrashDependedRepository = trashDependedRepository;
    }

    @NonNull
    @Override
    public List<T1> getByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return mTrashDependedRepository.getByProfile(profileId, paginationArgs);
    }

    @NonNull
    @Override
    public List<T1> getTrashByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return mTrashDependedRepository.getTrashByProfile(profileId, paginationArgs);
    }

    @Override
    public boolean moveToTrash(@NonNull String entityId) {
        return mTrashDependedRepository.moveToTrash(entityId);
    }

    @Override
    public boolean restoreFromTrash(@NonNull String entityId) {
        return mTrashDependedRepository.restoreFromTrash(entityId);
    }

    @Override
    public boolean removeForPermanent(@NonNull String entityId) {
        return mTrashDependedRepository.removeForPermanent(entityId);
    }
}
