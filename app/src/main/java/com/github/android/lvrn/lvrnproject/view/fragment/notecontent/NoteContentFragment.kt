package com.github.android.lvrn.lvrnproject.view.fragment.notecontent

import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteContentBinding
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.notedetails.NoteDetailsFragment
import com.github.android.lvrn.lvrnproject.view.util.consts.*
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import javax.inject.Inject

class NoteContentFragment : Fragment() {

    @set:Inject
    var mNotebookService: NotebookService? = null

    private var mSelectNote: Note? = null
    private var mNoteBookName: String? = null

    private var fragmentNotebookContentBinding: FragmentNoteContentBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentNotebookContentBinding = FragmentNoteContentBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        setUpToolbar()
        getParcelableDataAndSetInView()
        return fragmentNotebookContentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNotebookContentBinding!!.imBtnInformation.setOnClickListener { view1 -> startNoteDetailFragment() }
        fragmentNotebookContentBinding!!.imBtnArrowBackSingleNote!!.setOnClickListener { view1 -> backToPreviousFragment() }
        fragmentNotebookContentBinding!!.tvTagSingleNote!!.setOnClickListener { view1 -> openTagEditingDialog() }
    }

    /**
     * A method which creates new fragment and set arguments with bundle to this fragment
     */
    private fun startNoteDetailFragment() {
        val noteDetailsFragment = NoteDetailsFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_NOTEBOOK_NAME_KEY, mNoteBookName)
        bundle.putLong(BUNDLE_NOTE_UPDATED_KEY, mSelectNote!!.updateTime)
        bundle.putLong(BUNDLE_NOTE_CREATED_KEY, mSelectNote!!.creationTime)
        noteDetailsFragment.arguments = bundle
        openSelectFragment(noteDetailsFragment)
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
     * A method which replaces to container defined fragment with transition animation,
     * according as which device configuration
     *
     * @param fragment a fragment what replaces other and
     */
    private fun openSelectFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slideTransition = Slide(Gravity.RIGHT)
            slideTransition.duration = 1000
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000
            fragment.enterTransition = slideTransition
            fragment.returnTransition = null
            fragment.allowEnterTransitionOverlap = true
            fragment.sharedElementEnterTransition = changeBounds
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.constraint_container, fragment, TAG_NOTE_DETAIL_FRAGMENT)
                    .addSharedElement(fragmentNotebookContentBinding!!.imBtnInformation, fragmentNotebookContentBinding!!.imBtnInformation.transitionName)
                    .addToBackStack(null)
                    .commit()
            return
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.constraint_container, fragment, TAG_TAG_EDITING_DIALOG_FRAGMENT)
                .addToBackStack(null)
                .commit()
    }

    /**
     * A method which gets data from bundle and set them in defined view element
     */
    private fun getParcelableDataAndSetInView() {
        mSelectNote = arguments?.getParcelable(BUNDLE_NOTE_OBJECT_KEY)
        if (mSelectNote?.notebookId != null && !mSelectNote!!.notebookId.isEmpty()) {
            mNotebookService!!.openConnection()
            //TODO: clean it, use ifPresent method on Optional.
            val selectNotebook: Notebook = mNotebookService!!.getById(mSelectNote!!.notebookId).get()
            mNotebookService!!.closeConnection()
            mNoteBookName = selectNotebook.name
            fragmentNotebookContentBinding!!.tvNameNoteBook!!.text = mNoteBookName
        }
        fragmentNotebookContentBinding!!.editTextTitleSingleNote.setText(mSelectNote?.title)
        fragmentNotebookContentBinding!!.editTextMainContentForNote.setText(mSelectNote?.content)
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private fun setUpToolbar() {
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }
}