package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ActivityNoteDetailBinding
import com.github.android.lvrn.lvrnproject.view.activity.BaseActivity
import com.github.valhallalabs.laverna.persistent.entity.Note



class NoteDetailActivity : BaseActivity() {

    object Start {
        @JvmStatic
        fun onStart(context: Context, note: Note) {
            val intent: Intent = Intent(context, NoteDetailActivity::class.java).apply {
                putExtra("NOTE_DETAIL_KEY", note)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityNoteDetailBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideStartAnimation()
        binding = ActivityNoteDetailBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val parcelableExtra = intent.getParcelableExtra<Note>("NOTE_DETAIL_KEY")
        val noteViewModel = ViewModelProviders.of(this, NoteViewModelFactory(this)).get(NoteViewModel::class.java)
        noteViewModel.setNote(parcelableExtra)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }



}