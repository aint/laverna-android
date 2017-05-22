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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.HASH_TAG_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.HASH_TAG_REPLACEMENT;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.LINE_WITH_TASK_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.NEW_LINE_IN_LI_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.NEW_LINE_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.NEW_LINE_REPLACEMENT;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_A;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H1;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H2;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H3;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H4;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H5;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_H6;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_LI;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TAG_P;
import static com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserConst.TASK_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.impl.WebViewStyleConst.DOC_STYLE;
import static com.github.android.lvrn.lvrnproject.view.util.impl.WebViewStyleConst.HIGHLIGHT_JS_SCRIPT;

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

    @Override
    public String getParsedHtml(@NonNull String text) {
        Log.d(TAG, "Raw text:\n" + text);
        String textHtml = parseMarkdown(text);
        textHtml = additionalReplaces(textHtml);
        Log.d(TAG, "After parse:\n" + textHtml);
        return textHtml;
    }

    /**
     * A method which parses a text with markdown using a commonmark's parser.
     * @param text a text to parse.
     * @return a text after parsing.
     */
    private String parseMarkdown(String text) {
        Node node = parser.parse(text);
        return renderer.render(node);
    }

    /**
     * A method parses html using Jsoup, and makes additional replacements.
     * @param htmlText a text to parse.
     * @return a text with replacements.
     */
    private String additionalReplaces(String htmlText) {
        Document doc = Jsoup.parseBodyFragment(htmlText);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        addDocStyle(doc);

        makeTablesResponsive(doc);

        replaceInHeaders(doc);
        replaceInListItems(doc);
        replaceInParagraphs(doc);
        replaceInHyperLinks(doc);

        doc.body().append(HIGHLIGHT_JS_SCRIPT);

        htmlText = replaceGtAndLt(doc);
        return htmlText;
    }

    private void makeTablesResponsive(Document doc) {
        for (Element element : doc.getElementsByTag("table")) {
            element.wrap("<div style=\"overflow-x:auto;\">");
        }
    }


    /**
     * A method which replaces hash tags and tasks with html elements in the document's headers.
     * @param doc a document to parse.
     */
    private void replaceInHeaders(Document doc) {
        Arrays.asList(TAG_H1, TAG_H2, TAG_H3, TAG_H4, TAG_H5, TAG_H6).forEach(tag -> {
            for (Element element : doc.getElementsByTag(tag)) {
                String elementText = element.toString();
                elementText = elementText.substring(4, elementText.length() - 5);
                elementText = replaceTasks(elementText);
                elementText = elementText.replaceAll(HASH_TAG_REGEX, HASH_TAG_REPLACEMENT);
                element.text(elementText);
            }
        });
    }

    /**
     * A method which replaces hash tags, tasks and new lines with html elements in the document's
     * paragraphs.
     * @param doc a document to parse.
     */
    private void replaceInParagraphs(Document doc) {
        for (Element element : doc.getElementsByTag(TAG_P)) {
            String elementText = element.toString();
            elementText = elementText.substring(3, elementText.length() - 4);
            elementText = replaceTasks(elementText);
            elementText = elementText.replaceAll(HASH_TAG_REGEX, HASH_TAG_REPLACEMENT);
            elementText = elementText.replaceAll(NEW_LINE_REGEX, NEW_LINE_REPLACEMENT);
            element.text(elementText);
        }
    }

    /**
     * A method which replaces tasks and new lines with html elements in the document's hyperlinks.
     * @param doc a document to parse.
     */
    private void replaceInHyperLinks(Document doc) {
        for (Element element : doc.getElementsByTag(TAG_A)) {
            String elementText = element.toString();
            elementText = elementText.substring(3, elementText.length() - 4);
            elementText = replaceTasks(elementText);
            elementText = elementText.replaceAll(NEW_LINE_REGEX, NEW_LINE_REPLACEMENT);
            element.text(elementText);
        }
    }

    /**
     * A method which replaces tasks and new lines with html elements in the document's hyperlinks.
     * @param doc a document to parse.
     */
    private void replaceInListItems(Document doc) {
        for (Element element : doc.getElementsByTag(TAG_LI)) {
            String elementText = element.toString();
            elementText = elementText.substring(4, elementText.length() - 5);
            elementText = replaceTasks(elementText);
            elementText = elementText.replaceAll(HASH_TAG_REGEX, HASH_TAG_REPLACEMENT);
            elementText = elementText.replaceAll(NEW_LINE_IN_LI_REGEX, NEW_LINE_REPLACEMENT);
            element.text(elementText);
        }
    }

    /**
     * A method which finds and replaces task's brackets to checkboxes.
     * @param text a text to parse.
     * @return a text with replacements.
     */
    private String replaceTasks(String text) {
        Matcher lineWithTaskMatcher = Pattern.compile(LINE_WITH_TASK_REGEX).matcher(text);
        while (lineWithTaskMatcher.find()) {
            String lineWithTask = lineWithTaskMatcher.group();
            Matcher pureTaskMatcher = Pattern.compile(TASK_REGEX).matcher(lineWithTask);
            if (pureTaskMatcher.find()) {
                text = text.replace(lineWithTask, replaceBracketsWithCheckboxTag(pureTaskMatcher.group(0)));
                continue;
            }
            Log.wtf(TAG, "Parser filtered line with a task normally, but couldn't find task then");
            throw new RuntimeException();
        }
        return text;
    }

    /**
     * A method which replaces brackets with checkbox tags.
     * @param task a string which contains brackets.
     * @return a string with replaced brackets.
     */
    private String replaceBracketsWithCheckboxTag(String task) {
        String mark = Character.toString(task.charAt(1));
        String checkbox = "<input type=\"checkbox\" class=\"checkbox\"> ";
        if (mark.equals("x") || mark.equals("X")) {
            checkbox = "<input type=\"checkbox\" class=\"checkbox\" checked> ";
        }
        return checkbox.concat(task.substring(3).trim());
    }

   private String replaceGtAndLt(Document doc) {
       return doc.toString()
               .replaceAll("&lt;", "<")
               .replaceAll("&gt;", ">")
               .replaceAll("&amp;lt;", "<")
               .replaceAll("&amp;gt;", ">")
               .replaceAll("&amp;amp;lt;", "<")
               .replaceAll("&amp;amp;gt;", ">");
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
