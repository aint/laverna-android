package com.github.android.lvrn.lvrnproject.persistent.specification;

import android.database.Cursor;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface Specification {

    String toSqlQuery();

}
