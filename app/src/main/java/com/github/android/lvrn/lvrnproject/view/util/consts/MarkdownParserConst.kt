package com.github.android.lvrn.lvrnproject.view.util.consts

const val HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)"
const val HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>"

const val LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+"
const val TASK_REGEX = "\\[(x|X| ?)\\] .*"

const val NEW_LINE_REGEX = "\\n"
const val NEW_LINE_IN_LI_REGEX = "((?<!(</p>|</li>|</ul>|</ol>))\\n(?!(<p>|<li>)))"
const val NEW_LINE_REPLACEMENT = "<br />"

const val TAG_H1 = "h1"
const val TAG_H2 = "h2"
const val TAG_H3 = "h3"
const val TAG_H4 = "h4"
const val TAG_H5 = "h5"
const val TAG_H6 = "h6"
const val TAG_P = "p"
const val TAG_LI = "li"
const val TAG_A = "a"
const val TABLE_TAG = "table"
const val TABLE_WRAP_DIV_TAG = "<div style=\"overflow-x:auto;\">"
const val CHECKBOX_UNCHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\"> "
const val CHECKBOX_CHECKED_TAG = "<input type=\"checkbox\" class=\"checkbox\" checked> "

const val TASK_X_SMALL_MARK = "x"
const val TASK_X_BIG_MARK = "X"

const val LT1_CODE = "&lt;"
const val GT1_CODE = "&gt;"
const val LT2_CODE = "&amp;lt;"
const val GT2_CODE = "&amp;gt;"
const val LT3_CODE = "&amp;amp;lt;"
const val GT3_CODE = "&amp;amp;gt;"

const val LT_SYMBOL = "<"
const val GT_SYMBOL = ">"