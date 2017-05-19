package com.github.android.lvrn.lvrnproject.view.util.impl;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class MarkdownParserConsts {

    private static final String HASH_TAG_STYLE = ".tag {"
            + " display: inline;"
            + " background-color: #404040;"
            + " border-radius: 4px;"
            + " padding: 3px;"
            + " margin: 2$;"
            + " font-size: 80%;"
            + " color: white;"
            + " }\n";

    private static final String BLOCKQUOTE_STYLE = "blockquote {"
            + " font-size: 120%;"
            + " margin-top: 10px;"
            + " margin-bottom: 10px;"
            + " margin-left: 0px;"
            + " padding-left: 15px;"
            + " border-left: 3px solid #ccc;"
            + " }\n";

    private static final String TABLE_STYLE = "table, td, th {"
            + " border: 1px solid #ddd;"
            + " }\n"
            + " table {"
            + " border-collapse: collapse;"
            + " width: 100%;"
            + " }\n"
            + " th, td {"
            + " text-align: left;"
            + " padding: 8px;"
            + " }\n"
            + " tr:nth-child(even){"
            + " background-color: #f2f2f2"
            + " }\n";

    private static final String CHECKBOX_STYLE = ".checkbox {"
            + " position: relative;"
            + " margin-right: 5px;"
            + " cursor: pointer;"
            + " }\n"
            + " .checkbox:before {"
            + " transition: all 0.3s ease-in-out;"
            + " content: \"\";"
            + " position: absolute;"
            + " left: 0;"
            + " z-index: 1;"
            + " width: 1rem;"
            + " height: 1rem;"
            + " border: 2px solid #ccc;"
            + " }\n"
            + " .checkbox:checked:before {"
            + " transform: rotate(-45deg);"
            + " height: .5rem;"
            + " border-color: #009688;"
            + " border-top-style: none;"
            + " border-right-style: none;"
            + " }\n"
            + " .checkbox:after {"
            + " content: \"\";"
            + " position: absolute;"
            + " top: -0.125rem;"
            + " left: 0;"
            + " width: 1.1rem;"
            + " height: 1.1rem;"
            + " background: #fff;"
            + " cursor: pointer;"
            + " }\n";

    static final String DOC_STYLE = "<style> "
            + HASH_TAG_STYLE
            + BLOCKQUOTE_STYLE
            + TABLE_STYLE
            + CHECKBOX_STYLE
            + "</style>";


    static final String HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";
    static final String HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>";

    //TODO: fix it(new line, brackets after brackets etc.)
    static final String LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+";
    static final String TASK_REGEX = "\\[(x|X| ?)\\] .*";

}
