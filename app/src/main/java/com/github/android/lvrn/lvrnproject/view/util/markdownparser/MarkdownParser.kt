package com.github.android.lvrn.lvrnproject.view.util.markdownparser

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface MarkdownParser {
    /**
     * A method which converts a plain text with markdown into a html to display in a webview.
     * @param text a String object to convert.
     * @return a String object with html.
     */
    fun getParsedHtml(text: String): String
}
