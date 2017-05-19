package com.github.android.lvrn.lvrnproject.view.util;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface MarkdownParser {

    /**
     * A method which converts a plain text with markdown into a html to display in a webview.
     * @param text a String object to convert.
     * @return a String object with html.
     */
    String getParsedHtml(String text);
}
