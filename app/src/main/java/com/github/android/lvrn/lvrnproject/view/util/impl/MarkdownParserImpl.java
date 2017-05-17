package com.github.android.lvrn.lvrnproject.view.util.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.android.lvrn.lvrnproject.view.util.MarkdownParser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class MarkdownParserImpl implements MarkdownParser {
    private static final String TAG = "MarkdownParserImpl";

    private static final String HASH_TAG_STYLE = "<style> " +
            ".tag {" +
            "display: inline; " +
            "background-color: #404040; " +
            "border-radius: 4px; " +
            "padding: 3px; " +
            "margin: 2$; " +
            "font-size: 80%; " +
            "color: white; " +
            "} " +
            "\n" +
            "blockquote {\n" +
            "  font-size: 120%;\n" +
            "  margin-top: 10px;\n" +
            "  margin-bottom: 10px;\n" +
            "  margin-left: 0px;\n" +
            "  padding-left: 15px;\n" +
            "  border-left: 3px solid #ccc;\n" +
            "} " +
            "\n" +
            "table, td, th {    \n" +
            "    border: 1px solid #ddd;\n" +
            "}\n" +
            "table {\n" +
            "    border-collapse: collapse;\n" +
            "    width: 100%;\n" +
            "}\n" +
            "\n" +
            "th, td {\n" +
            "    text-align: left;\n" +
            "    padding: 8px;\n" +
            "}\n" +
            "\n" +
            "tr:nth-child(even){background-color: #f2f2f2}" +

            ".checkbox {\n" +
            "position: relative;\n" +
            "margin-right: 5px;\n" +
            "cursor: pointer;\n" +
            "}\n" +
            " \n" +
            ".checkbox:before {\n" +
            "  transition: all 0.3s ease-in-out;\n" +
            "   content: \"\";\n" +
            "   position: absolute;\n" +
            "   left: 0;\n" +
            "   z-index: 1;\n" +
            "   width: 1rem;\n" +
            "   height: 1rem;\n" +
            "   border: 2px solid #ccc;\n" +
            " }\n" +
            "  \n" +
            " .checkbox:checked:before {\n" +
            "   transform: rotate(-45deg);\n" +
            "  height: .5rem;\n" +
            "   border-color: #009688;\n" +
            "   border-top-style: none;\n" +
            "   border-right-style: none;\n" +
            " }\n" +
            "  \n" +
            " .checkbox:after {\n" +
            "   content: \"\";\n" +
            "   position: absolute;\n" +
            "   top: -0.125rem;\n" +
            "   left: 0;\n" +
            "   width: 1.1rem;\n" +
            "   height: 1.1rem;\n" +
            "   background: #fff;\n" +
            "   cursor: pointer;\n" +
            " }\n"+
            "</style>";

    private static final String HASH_TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";
    private static final String HASH_TAG_REPLACEMENT = "<span class=\"tag\">#$1</span>";

    //TODO: fix it(new line, brackets after brackets etc.)
    private static final String UNCOMPLETED_TASK_REGEX = "(\\[\\]|\\[ \\])";
    private static final String UNCOMPLETED_TASK_REPLACEMENT = "<input type=\"checkbox\" class=\"checkbox\">";

    private static final String COMPLETED_TASK_REGEX = "(\\[x\\]|\\[X\\])";
    private static final String COMPLETED_TASK_REPLACEMENT = "<input type=\"checkbox\" class=\"checkbox\" checked>";

    private Parser parser;

    private HtmlRenderer renderer;

    public MarkdownParserImpl () {
        parser = getParser();
        renderer = getHtmlRender();
    }

    //TODO: check in next milestone. If laverna's parser is not used, then modify parsing with custom notes of commonmark.
    //TODO: remove apache from dependecies latter.
    public String getParsedHtml(@NonNull String text) {
        Log.d(TAG, "Note content with markdown:\n" + text);
        Node node = parser.parse(text);
        String textHtml = renderer.render(node);
        textHtml = replaceAllNewLinesWithBrTag(textHtml);
        textHtml = parseTasks(textHtml);
        Log.d(TAG, "Parsed to html note content:\n" + StringEscapeUtils.escapeJava(textHtml));
        return textHtml;
    }

    private String parseTasks(String text) {
        return text
                .replaceAll(UNCOMPLETED_TASK_REGEX, UNCOMPLETED_TASK_REPLACEMENT)
                .replaceAll(COMPLETED_TASK_REGEX, COMPLETED_TASK_REPLACEMENT);
    }

    private String replaceAllNewLinesWithBrTag(String htmlText) {
        Document doc = Jsoup.parseBodyFragment(htmlText);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

        Element head = doc.head();
        head.append(HASH_TAG_STYLE);

        Elements pElements = doc.getElementsByTag("p");
        for(Element element : pElements) {
            String elementText = element.toString();
            elementText = elementText.substring(3, elementText.length() - 4);
            element.text(elementText
                    .replaceAll(HASH_TAG_REGEX, HASH_TAG_REPLACEMENT)
                    .replaceAll("\\n", " <br /> "));
        }
        return doc.toString()
                .replaceAll("&lt;", "<") //TODO: not all must be changed;
                .replaceAll("&gt;", ">");
//                .replaceAll("&lt;br /&gt;", " <br /> ");
//                .replaceAll("&lt;span class=\"tag\"&gt;", "<span class=\"tag\">")
//                .replaceAll("&lt;/span&gt;", "</span>");
    }

    /**
     * A method which returns Parser which parses input text to a tree of nodes.
     * @return a Parser object.
     */
    private Parser getParser() {
        return Parser.builder()
                .extensions(getExtensions())
                .build();
    }

    /**
     * A method which returns HtmlRender which renders a tree of nodes to HTML.
     * @return a HtmlRender object.
     */
    private HtmlRenderer getHtmlRender() {
        return HtmlRenderer.builder()
                .extensions(getExtensions())
                .build();
    }

    /**
     * A method which returns a list of Extension objects for Parser and a HtmlRender.
     * @return a list of extensions.
     */
    @NonNull
    private List<Extension> getExtensions() {
        return Arrays.asList(
                TablesExtension.create(),
                AutolinkExtension.create(),
                StrikethroughExtension.create());
    }
}
