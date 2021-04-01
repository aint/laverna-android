package com.github.android.lvrn.lvrnproject.view.fragment.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteDetailsBinding
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTEBOOK_NAME_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTE_CREATED_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTE_UPDATED_KEY
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString

class NoteDetailsFragment : Fragment(){

    private var fragmentNoteDetailsBinding: FragmentNoteDetailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentNoteDetailsBinding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        setUpToolbar()
        getParcelableDataAndSetInView()
        return fragmentNoteDetailsBinding?.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNoteDetailsBinding!!.imBtnArrowBack.setOnClickListener { view1 -> backToPreviousFragment() }
    }

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    fun backToPreviousFragment() {
        activity?.onBackPressed()
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private fun setUpToolbar() {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    /**
     * A method which gets data from fragment argument bundles and sets their in defined views
     */
    private fun getParcelableDataAndSetInView() {
        fragmentNoteDetailsBinding!!.tvUpdateDate.text = convertMillisecondsToString(requireArguments().getLong(BUNDLE_NOTE_UPDATED_KEY))
        fragmentNoteDetailsBinding!!.tvCreateDate.text = convertMillisecondsToString(requireArguments().getLong(BUNDLE_NOTE_CREATED_KEY))
        fragmentNoteDetailsBinding!!.tvNotebookNameDetailNote.text = requireArguments().getString(BUNDLE_NOTEBOOK_NAME_KEY)
    }
}