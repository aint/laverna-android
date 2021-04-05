package com.github.android.lvrn.lvrnproject.view.fragment.notecontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteContentBinding
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteViewModel
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTE_ID_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_TAG_EDITING_DIALOG_FRAGMENT
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import javax.inject.Inject

class NoteContentFragment : Fragment() {

    @set:Inject
    var mNotebookService: NotebookService? = null

    private var mSelectNote: Note? = null
    private var mNoteBookName: String? = null

    private var fragmentNotebookContentBinding: FragmentNoteContentBinding? = null
    private val viewModel : NoteViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentNotebookContentBinding = FragmentNoteContentBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        return fragmentNotebookContentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSelectNote = viewModel.note.value
        setDataAndSetInView()
        fragmentNotebookContentBinding!!.imBtnInformation.setOnClickListener { view1 -> startNoteDetailFragment() }
        fragmentNotebookContentBinding!!.imBtnArrowBackSingleNote!!.setOnClickListener { view1 -> backToPreviousFragment() }
        fragmentNotebookContentBinding!!.tvTagSingleNote!!.setOnClickListener { view1 -> openTagEditingDialog() }
    }

    /**
     * A method which creates new fragment and set arguments with bundle to this fragment
     */
    private fun startNoteDetailFragment() {
        findNavController().navigate(R.id.action_noteContentFragment_to_noteDetailsFragment)
    }

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    private fun backToPreviousFragment() {
        activity?.onBackPressed()
    }

    /**
     * A method which hears when user click on button and replaces to container defined fragment
     * and set arguments with bundle to this fragment
     */
    fun openTagEditingDialog() {
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
        val bundle = Bundle()
        bundle.putString(BUNDLE_NOTE_ID_KEY, mSelectNote!!.id)
        val dialogFragment: DialogFragment = TagEditingDialogFragmentImpl()
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentTransaction, TAG_TAG_EDITING_DIALOG_FRAGMENT)
    }

    /**
     * A method which gets data from bundle and set them in defined view element
     */
    private fun setDataAndSetInView() {
        if (mSelectNote?.notebookId != null && !mSelectNote!!.notebookId.isEmpty()) {
            mNotebookService!!.openConnection()
            //TODO: clean it, use ifPresent method on Optional.
            val selectNotebook: Notebook = mNotebookService!!.getById(mSelectNote!!.notebookId).get()
            mNotebookService!!.closeConnection()
            mNoteBookName = selectNotebook.name
            fragmentNotebookContentBinding!!.tvNameNoteBook!!.text = mNoteBookName
            viewModel.setNotebook(selectNotebook)
        }
        fragmentNotebookContentBinding!!.editTextTitleSingleNote.setText(mSelectNote?.title)
        fragmentNotebookContentBinding!!.editTextMainContentForNote.setText(mSelectNote?.content)
    }

}