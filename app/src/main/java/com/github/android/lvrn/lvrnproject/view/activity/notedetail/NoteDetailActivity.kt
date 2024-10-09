package com.github.android.lvrn.lvrnproject.view.activity.notedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ActivityNoteDetailBinding
import com.github.android.lvrn.lvrnproject.view.activity.BaseActivity
import com.github.valhallalabs.laverna.persistent.entity.Note
import javax.inject.Inject


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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideStartAnimation()
        binding = ActivityNoteDetailBinding.inflate(layoutInflater);
        setContentView(binding.root)
        LavernaApplication.getsAppComponent().inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        val parcelableExtra = intent.getParcelableExtra<Note>("NOTE_DETAIL_KEY")
        parcelableExtra?.let {
            viewModel.setNote(parcelableExtra)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
        }

    }


}