package com.github.android.lvrn.lvrnproject.view.listener

import androidx.appcompat.widget.SearchView
import io.reactivex.subjects.ReplaySubject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class SearchViewOnQueryTextListener(private val mQueryReplaySubject: ReplaySubject<String>) :
    SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        mQueryReplaySubject.onNext(newText)
        return true
    }
}
