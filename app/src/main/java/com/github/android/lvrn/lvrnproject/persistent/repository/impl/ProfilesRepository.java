package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.Repository;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.*;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfilesRepository extends RepositoryAbstractImpl<ProfileEntity> {

    public ProfilesRepository() {
        super(TABLE_NAME);
    }

    @Override
    protected ContentValues toContentValues(ProfileEntity item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, item.getId());
        contentValues.put(COLUMN_PROFILE_NAME, item.getName());
        return contentValues;
    }

    @Override
    protected ProfileEntity toEntity(Cursor cursor) {
        return new ProfileEntity(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_NAME)));
    }
}
