package com.github.android.lvrn.lvrnproject.persistent.specification.profilesspecification;

import android.database.Cursor;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.specification.Specification;

import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.*;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class GetAllProfilesSpec implements Specification {

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM " + ProfilesTable.TABLE_NAME;
    }
}
