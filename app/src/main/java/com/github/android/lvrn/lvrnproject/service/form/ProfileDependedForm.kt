package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

abstract class ProfileDependedForm<T : ProfileDependedEntity>(protected open val profileId: String) : Form<T>