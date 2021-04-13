package com.github.android.lvrn.lvrnproject.view.activity

import androidx.appcompat.app.AppCompatActivity
import com.github.android.lvrn.lvrnproject.R

open class BaseActivity : AppCompatActivity() {

    protected open fun overrideStartAnimation() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    protected open fun overrideFinishAnimation() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overrideFinishAnimation()
    }
}