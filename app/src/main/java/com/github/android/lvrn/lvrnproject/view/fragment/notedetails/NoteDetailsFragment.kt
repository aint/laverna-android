package com.github.android.lvrn.lvrnproject.view.fragment.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteDetailsBinding
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteViewModel
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class NoteDetailsFragment : Fragment() {

    private var fragmentNoteDetailsBinding: FragmentNoteDetailsBinding? = null
    private val viewModel: NoteViewModel by activityViewModels()
    private var mSelectNote: Note? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentNoteDetailsBinding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        mSelectNote = viewModel.note.value
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
     * A method which gets data from fragment argument bundles and sets their in defined views
     */
    private fun getParcelableDataAndSetInView() {
        fragmentNoteDetailsBinding!!.tvUpdateDate.text = convertMillisecondsToString(mSelectNote!!.updateTime)
        fragmentNoteDetailsBinding!!.tvCreateDate.text = convertMillisecondsToString(mSelectNote!!.creationTime)
        fragmentNoteDetailsBinding!!.tvNotebookNameDetailNote.text = viewModel.notebook.value?.name
    }
}