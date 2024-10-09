package com.github.android.lvrn.lvrnproject.dagger.modules

import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UtilModule {

    @Binds
    abstract fun bindMarkdownParser(noteRepository: MarkdownParserImpl): MarkdownParser
}