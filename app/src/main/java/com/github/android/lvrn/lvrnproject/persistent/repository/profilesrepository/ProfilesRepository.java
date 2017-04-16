package com.github.android.lvrn.lvrnproject.persistent.repository.profilesrepository;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.Repository;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;
import com.github.android.lvrn.lvrnproject.persistent.specification.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.ProfilesTable.*;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfilesRepository extends RepositoryAbstractImpl<Profile> implements Repository<Profile> {


    public ProfilesRepository() {
        super(TABLE_NAME);
    }

    @Override
    public void add(Profile item) {
        add(Collections.singletonList(item));
    }

    @Override
    public void add(Iterable<Profile> items) {
        List<ContentValues> contentValues = new ArrayList<>();
        for (Profile profile : items) {
            contentValues.add(toContentValues(profile));
        }
        super.addToDb(contentValues);
    }

    @Override
    public void update(Profile item) {
        super.updateInDb(toContentValues(item));
    }

    @Override
    public void remove(Profile item) {
        super.removeFromDb(item.getId());
    }

    @Override
    public void remove(Specification specification) {}

    @Override
    public List<Profile> query(Specification specification) {
        return null;
    }

    @Override
    protected ContentValues toContentValues(Profile item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, item.getId());
        contentValues.put(COLUMN_PROFILE_NAME, item.getName());
        return contentValues;
    }

    @Override
    protected Profile toItem(Cursor cursor) {
        return null;
    }
}
