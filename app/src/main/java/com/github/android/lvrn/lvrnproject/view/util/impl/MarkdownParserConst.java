package com.github.android.lvrn.lvrnproject.view.util.impl;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class MarkdownParserConst {

    static final String HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";
    static final String HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>";

    //TODO: fix it(new line, brackets after brackets etc.)
    static final String LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+";
    static final String TASK_REGEX = "\\[(x|X| ?)\\] .*";

    static final String NEW_LINE_REGEX = "\\n";
    static final String NEW_LINE_IN_LI_REGEX = "((?<!(</p>|</li>|</ul>|</ol>))\\n(?!(<p>|<li>)))";
    static final String NEW_LINE_REPLACEMENT = "<br />";

    static final String TAG_H1 = "h1";
    static final String TAG_H2 = "h2";
    static final String TAG_H3 = "h3";
    static final String TAG_H4 = "h4";
    static final String TAG_H5 = "h5";
    static final String TAG_H6 = "h6";
    static final String TAG_P = "p";
    static final String TAG_LI = "li";
    static final String TAG_A = "a";

}
