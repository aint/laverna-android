package com.github.android.lvrn.lvrnproject.view.dialog.tagediting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentTagEditingBinding
import com.github.android.lvrn.lvrnproject.service.core.TagService
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TagsListAdapter
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTE_ID_KEY
import com.github.valhallalabs.laverna.persistent.entity.Tag
import javax.inject.Inject

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class TagEditingDialogFragmentImpl : DialogFragment() {
    @JvmField
    @Inject
    var mTagService: TagService? = null
    private val mTagListDate: MutableList<Tag> = ArrayList()
    private var dialogFragmentTagEditingBinding: DialogFragmentTagEditingBinding? = null

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogFragmentTagEditingBinding =
            DialogFragmentTagEditingBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        initRecyclerView()
        initTagData()
        return dialogFragmentTagEditingBinding!!.root
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        val recyclerViewAllTags = dialogFragmentTagEditingBinding!!.recyclerViewAllTags
        recyclerViewAllTags.layoutManager = layoutManager
        val tagEditingAdapter = TagsListAdapter(mTagListDate)
        recyclerViewAllTags.adapter = tagEditingAdapter
    }

    private fun initTagData() {
        mTagService!!.openConnection()
        mTagListDate.addAll(mTagService!!.getByNote(requireArguments().getString(BUNDLE_NOTE_ID_KEY)!!))
        mTagService!!.closeConnection()
    }
}
