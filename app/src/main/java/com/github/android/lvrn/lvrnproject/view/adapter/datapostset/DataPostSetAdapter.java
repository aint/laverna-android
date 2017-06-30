package com.github.android.lvrn.lvrnproject.view.adapter.datapostset;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class DataPostSetAdapter<T1 extends ProfileDependedEntity, T2 extends ViewHolder> extends Adapter<T2> {

    public abstract void setData(List<T1> data);

}
