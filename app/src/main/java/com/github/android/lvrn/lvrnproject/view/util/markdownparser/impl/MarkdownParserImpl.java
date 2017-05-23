package com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser;
import com.orhanobut.logger.Logger;

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

import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.CHECKBOX_CHECKED_TAG;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.CHECKBOX_UNCHECKED_TAG;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.GT1_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.GT2_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.GT3_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.GT_SYMBOL;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.HASH_TAG_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.HASH_TAG_REPLACEMENT;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.LINE_WITH_TASK_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.LT1_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.LT2_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.LT3_CODE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.LT_SYMBOL;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.NEW_LINE_IN_LI_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.NEW_LINE_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.NEW_LINE_REPLACEMENT;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TABLE_TAG;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TABLE_WRAP_DIV_TAG;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_A;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H1;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H2;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H3;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H4;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H5;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_H6;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_LI;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TAG_P;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TASK_REGEX;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TASK_X_BIG_MARK;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserConst.TASK_X_SMALL_MARK;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.WebViewStyleConst.DOC_STYLE;
import static com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.WebViewStyleConst.HIGHLIGHT_JS_SCRIPT;

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
        Logger.d("Raw text:\n $s", text);
        String textHtml = parseMarkdown(text);
        textHtml = additionalReplaces(textHtml);
        Logger.d("After parse:\n $s", textHtml);
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
        Document doc = getParsedHtmlDocument(htmlText);
        makeTablesResponsive(doc);
        replaceInHeaders(doc);
        replaceInListItems(doc);
        replaceInParagraphs(doc);
        replaceInHyperLinks(doc);
        return replaceGtAndLt(doc);
    }

    /**
     * A method which parses html using Jsoup,
     * @param htmlText a text to parse.
     * @return a document with parsed text.
     */
    private Document getParsedHtmlDocument(String htmlText) {
        Document doc = Jsoup.parseBodyFragment(htmlText);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        doc.head().append(DOC_STYLE);
        doc.body().append(HIGHLIGHT_JS_SCRIPT);
        return doc;
    }

    /**
     * A method which wraps a table element with div blocks to make table responsive.
     * @param doc a document to parse.
     */
    private void makeTablesResponsive(Document doc) {
        for (Element element : doc.getElementsByTag(TABLE_TAG)) {
            element.wrap(TABLE_WRAP_DIV_TAG);
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
            Logger.wtf("Parser filtered line with a task normally, but couldn't find task then");
            throw new RuntimeException();
        }
        return text;
    }

    /**
     * A method which replaces brackets with checkbox tags.
     * @param task a string which contains brackets.
     * @return a string with replaced brackets.
     */
    @NonNull
    private String replaceBracketsWithCheckboxTag(String task) {
        String mark = Character.toString(task.charAt(1));
        String checkbox = CHECKBOX_UNCHECKED_TAG;
        if (mark.equals(TASK_X_SMALL_MARK) || mark.equals(TASK_X_BIG_MARK)) {
            checkbox = CHECKBOX_CHECKED_TAG;
        }
        return checkbox.concat(task.substring(3).trim());
    }

    /**
     * A method which replace all lt and gt symbols.
     * @param doc a document to parse.
     * @return a parsed document.
     */
    private String replaceGtAndLt(Document doc) {
       return doc.toString()
               .replaceAll(LT1_CODE, LT_SYMBOL)
               .replaceAll(GT1_CODE, GT_SYMBOL)
               .replaceAll(LT2_CODE, LT_SYMBOL)
               .replaceAll(GT2_CODE, GT_SYMBOL)
               .replaceAll(LT3_CODE, LT_SYMBOL)
               .replaceAll(GT3_CODE, GT_SYMBOL);
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
