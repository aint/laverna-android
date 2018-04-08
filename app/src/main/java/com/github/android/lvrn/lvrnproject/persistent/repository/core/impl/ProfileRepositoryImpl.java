package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.BasicRepositoryImpl;
import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.COLUMN_PROFILE_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.TABLE_NAME;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileRepositoryImpl extends BasicRepositoryImpl<Profile> implements ProfileRepository {

    public ProfileRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Profile entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_NAME, entity.getName());
        return contentValues;
    }

    @NonNull
    @Override
    protected Profile toEntity(@NonNull Cursor cursor) {
        return new Profile(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_NAME)));
    }

    @NonNull
    @Override
    public List<Profile> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getByRawQuery(query);
    }

    @Override
    public Optional<Profile> getByName(String name) {
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + TABLE_NAME
                        + " WHERE " + COLUMN_PROFILE_NAME + " = ?",
                new String[]{ name });
        Logger.d("Table name: %s\nOperation: getByName\nName: %s\nCursor: %s", TABLE_NAME, name, (cursor != null));
        if (cursor != null && cursor.moveToFirst()) {
            return Optional.of(toEntity(cursor));
        }
        return Optional.absent();
    }
}
