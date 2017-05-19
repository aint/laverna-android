package com.github.android.lvrn.lvrnproject.view.util.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.android.lvrn.lvrnproject.view.util.MarkdownParser;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConsts.DOC_STYLE;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConsts.HASH_TAG_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConsts.HASH_TAG_REPLACEMENT;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConsts.LINE_WITH_TASK_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConsts.TASK_REGEX;
import static org.apache.commons.lang3.StringEscapeUtils.escapeJava;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class MarkdownParserImpl implements MarkdownParser {
    private static final String TAG = "MarkdownParserImpl";

    private Parser parser;

    private HtmlRenderer renderer;

    public MarkdownParserImpl () {
        parser = getParser();
        renderer = getHtmlRender();
    }

    public String getParsedHtml(@NonNull String text) {
        Log.d(TAG, "Note content with markdown:\n" + escapeJava(text));
        text = parseTasks(text);
        Log.d(TAG, "Note content after tasks:\n" + escapeJava(text));
        String textHtml = parseMarkdown(text);
        Log.d(TAG, "After first parse :\n" + escapeJava(textHtml));
        textHtml = replaceAllNewLinesWithBrTag(textHtml);
        Log.d(TAG, "Parsed to html note content:\n" + escapeJava(textHtml));
        return textHtml;
    }

    private String parseMarkdown(String text) {
        Node node = parser.parse(text);
        return renderer.render(node);
    }

    private String parseTasks(String text) {
        Matcher lineWithTaskMatcher = Pattern.compile(LINE_WITH_TASK_REGEX).matcher(text);
        while (lineWithTaskMatcher.find()) {
            String lineWithTask = lineWithTaskMatcher.group();
            Matcher pureTaskMatcher = Pattern.compile(TASK_REGEX).matcher(lineWithTask);
            if (pureTaskMatcher.find()) {
                text = text.replace(lineWithTask, replaceBracketsWithCheckbox(pureTaskMatcher.group(0)));
                continue;
            }
            Log.wtf(TAG, "Parser filtered line with a task normally, but couldn't find task then");
            throw new RuntimeException();
        }
        return text;
    }

    private String replaceBracketsWithCheckbox(String task) {
        String mark = Character.toString(task.charAt(1));
        String checkbox = "<input type=\"checkbox\" class=\"checkbox\"> ";
        if (mark.equals("x") || mark.equals("X")) {
            checkbox = "<input type=\"checkbox\" class=\"checkbox\" checked> ";
        }
        return checkbox.concat(task.substring(3).trim());
    }







    private String replaceAllNewLinesWithBrTag(String htmlText) {
        Document doc = Jsoup.parseBodyFragment(htmlText);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

        addDocStyle(doc);


        Elements pElements = doc.getElementsByTag("p");
        for(Element element : pElements) {
            String elementText = element.toString();
            System.out.println("ELEMENT TEXT: " + escapeJava(elementText));
            elementText = elementText.substring(3, elementText.length() - 4);
            element.text(elementText
                    .replaceAll(HASH_TAG_REGEX, HASH_TAG_REPLACEMENT)
                    .replaceAll("\\n", " <br /> "));
            System.out.println("AFTER ELEMENT TEXT: " + escapeJava(elementText));
        }
        return doc.toString()
                .replaceAll("&lt;", "<") //TODO: not all must be changed;
                .replaceAll("&gt;", ">");
//                .replaceAll("&lt;br /&gt;", " <br /> ");
//                .replaceAll("&lt;span class=\"tag\"&gt;", "<span class=\"tag\">")
//                .replaceAll("&lt;/span&gt;", "</span>");
    }

    /**
     * A method which adds style in the head of the document.
     * @param doc a document to add style.
     */
    private void addDocStyle(Document doc) {
        Element head = doc.head();
        head.append(DOC_STYLE);
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
