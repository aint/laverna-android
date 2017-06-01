package com.github.android.lvrn.lvrnproject.view.fragment;

import android.support.v4.app.Fragment;

//import com.github.android.lvrn.lvrnproject.view.adapter.impl.AllNotebooksAdapter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookContentFragmentImpl extends Fragment {
//    @Inject
//    NoteService mNoteService;
//    @Inject
//    NotebookService mNotebookService;
//    @BindView(R.id.recycler_view_notebook_together)
//    RecyclerView mRecyclerViewNotebook;
//    @BindView(R.id.recycler_view_notes_together)
//    RecyclerView mRecyclerViewNote;
//    private Unbinder mUnbinder;
//    private List<Note> mNoteData = new ArrayList<>();
//    private List<Notebook> mNotebookData = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_notebok_content, container, false);
//        mUnbinder = ButterKnife.bind(this, view);
//        LavernaApplication.getsAppComponent().inject(this);
//        initRecyclerView();
////        hardcodeNote();
////        hardcodeNotebook();
////        mNoteData.addAll(mNoteService.getByProfile(CurrentState.profileId,1,10));
////        mNotebookData.addAll(mNotebookService.getByProfile(CurrentState.profileId,1,10));
////        mNoteService.closeConnection();
////        mNotebookService.closeConnection();
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if(mUnbinder != null){
//            mUnbinder.unbind();
//        }
//    }
//
//    /**
//     * A method which initializes recycler view
//     */
//    private void initRecyclerView() {
//        mRecyclerViewNotebook.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerViewNote.setLayoutManager(new LinearLayoutManager(getActivity()));
////        AllNotesAdapter notesRecyclerViewAdapter = new AllNotesAdapter(mNoteData);
//        AllNotebooksAdapter allNotebooksAdapter = new AllNotebooksAdapter(mNotebookData);
//        mRecyclerViewNotebook.setAdapter(allNotebooksAdapter);
////        mRecyclerViewNote.setAdapter(notesRecyclerViewAdapter);
//    }
//
////    //TODO: temporary, remove later
////    private void hardcodeNote() {
////        mNoteService.openConnection();
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Dog", "Content 1", "Content 1", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Cat", "Content 2", "Content 2", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Bird", "Content 3", "Content 3", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Pig", "Content 4", "Content 4", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Tiger", "Content 5", "Content 5", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Duck", "Content 6", "Content 6", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Wild Cat", "Content 7", "Content 7", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Goose", "Content 8", "Content 8", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Rat", "Content 9", "Content 9", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Butterfly", "Content 10", "Content 10", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Elephant", "Content 11", "Content 11", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Chicken", "Content 12", "Content 12", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Cock", "Content 13", "Content 13", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Bug", "Content 14", "Content 14", false));
////        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Snake", "Content 15", "Content 15", false));
////    }
////
////    //TODO: temporary, remove later
////    private void hardcodeNotebook(){
////        mNotebookService.openConnection();
////        mNotebookService.create(new NotebookForm(CurrentState.profileId, null, "Animals"));
////    }
//

}
