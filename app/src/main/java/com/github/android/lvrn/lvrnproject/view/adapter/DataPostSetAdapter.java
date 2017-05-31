package com.github.android.lvrn.lvrnproject.view.adapter;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface DataPostSetAdapter<T1 extends ProfileDependedEntity> {

    void setData(List<T1> data);

}
