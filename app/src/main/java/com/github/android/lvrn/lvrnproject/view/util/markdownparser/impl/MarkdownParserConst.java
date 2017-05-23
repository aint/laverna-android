package com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class MarkdownParserConst {

    private MarkdownParserConst() {
    }

    static final String HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";
    static final String HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>";

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
    static final String TABLE_TAG = "table";
    static final String TABLE_WRAP_DIV_TAG = "<div style=\"overflow-x:auto;\">";
    static final String CHECKBOX_UNCHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\"> ";
    static final String CHECKBOX_CHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\" checked> ";

    static final String TASK_X_SMALL_MARK = "x";
    static final String TASK_X_BIG_MARK = "X";

    static final String LT1_CODE = "&lt;";
    static final String GT1_CODE = "&gt;";
    static final String LT2_CODE = "&amp;lt;";
    static final String GT2_CODE = "&amp;gt;";
    static final String LT3_CODE = "&amp;amp;lt;";
    static final String GT3_CODE = "&amp;amp;gt;";

    static final String LT_SYMBOL = "<";
    static final String GT_SYMBOL = ">";
}
