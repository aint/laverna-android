package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.view.util.TimeUtilKt.convertMillisecondsToString;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.FavouriteViewHolder> implements DataPostSetAdapter<Note> {

    private FavouritesListFragment mAllNotesFragment;

    private List<Note> mNotes;

    private FavouritesListPresenter mFavouriteListPresenter;

    public FavouritesListAdapter(FavouritesListFragment allNotesFragment, FavouritesListPresenter favouritesListPresenter) {
        mAllNotesFragment = allNotesFragment;
        mFavouriteListPresenter = favouritesListPresenter;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.itemNoteBinding.tvTitleNote.setText(note.getTitle());
        holder.itemNoteBinding.tvPromptTextNote.setText(note.getContent());
        holder.itemNoteBinding.tvDateCreatedNote.setText(convertMillisecondsToString(note.getCreationTime()));
        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(note));
        holder.itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp);
        holder.itemNoteBinding.imBtnFavorite.setOnClickListener(view -> {
            mFavouriteListPresenter.changeNoteFavouriteStatus(note, position, view);
            mNotes.remove(note);
            notifyItemRemoved(position);
            if (mNotes.isEmpty()){
                mAllNotesFragment.showEmptyScreen();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding itemNoteBinding;

        FavouriteViewHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }
}


