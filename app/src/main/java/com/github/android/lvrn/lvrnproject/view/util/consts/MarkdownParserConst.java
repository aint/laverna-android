package com.github.android.lvrn.lvrnproject.view.util.consts;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public final class MarkdownParserConst {

    private MarkdownParserConst() {}

    public static final String HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";
    public static final String HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>";

    public static final String LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+";
    public static final String TASK_REGEX = "\\[(x|X| ?)\\] .*";

    public static final String NEW_LINE_REGEX = "\\n";
    public static final String NEW_LINE_IN_LI_REGEX = "((?<!(</p>|</li>|</ul>|</ol>))\\n(?!(<p>|<li>)))";
    public static final String NEW_LINE_REPLACEMENT = "<br />";

    public static final String TAG_H1 = "h1";
    public static final String TAG_H2 = "h2";
    public static final String TAG_H3 = "h3";
    public static final String TAG_H4 = "h4";
    public static final String TAG_H5 = "h5";
    public static final String TAG_H6 = "h6";
    public static final String TAG_P = "p";
    public static final String TAG_LI = "li";
    public static final String TAG_A = "a";
    public static final String TABLE_TAG = "table";
    public static final String TABLE_WRAP_DIV_TAG = "<div style=\"overflow-x:auto;\">";
    public static final String CHECKBOX_UNCHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\"> ";
    public static final String CHECKBOX_CHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\" checked> ";

    public static final String TASK_X_SMALL_MARK = "x";
    public static final String TASK_X_BIG_MARK = "X";

    public static final String LT1_CODE = "&lt;";
    public static final String GT1_CODE = "&gt;";
    public static final String LT2_CODE = "&amp;lt;";
    public static final String GT2_CODE = "&amp;gt;";
    public static final String LT3_CODE = "&amp;amp;lt;";
    public static final String GT3_CODE = "&amp;amp;gt;";

    public static final String LT_SYMBOL = "<";
    public static final String GT_SYMBOL = ">";
}
