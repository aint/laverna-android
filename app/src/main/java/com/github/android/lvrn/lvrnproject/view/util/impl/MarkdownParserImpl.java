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
    public static final String H1 = "h1";
    public static final String H2 = "h2";
    public static final String H3 = "h3";
    public static final String H4 = "h4";
    public static final String H5 = "h5";
    public static final String H6 = "h6";
    public static final String P = "p";
    public static final String LI = "li";
    public static final String NEW_LINE_REGEX = "\\n";
    public static final String NEW_LINE_REPLACEMENT = "<br />";
    public static final String A = "a";

    private Parser parser;

    private HtmlRenderer renderer;

    public MarkdownParserImpl () {
        parser = getParser();
        renderer = getHtmlRender();
    }

    public String getParsedHtml(@NonNull String text) {
        Log.d(TAG, "Note content with markdown:\n" + escapeJava(text));
//        text = parseTasks(text);
        String textHtml = parseMarkdown(text);
        Log.d(TAG, "After first parse :\n" + escapeJava(textHtml));
        textHtml = replaceAll(textHtml);
        Log.d(TAG, "Parsed to html note content:\n" + escapeJava(textHtml));
        return textHtml;
    }

    private String parseMarkdown(String text) {
        Node node = parser.parse(text);
        return renderer.render(node);
    }











    private String replaceAll(String htmlText) {
        System.out.println("");
        Document doc = Jsoup.parseBodyFragment(htmlText);
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));


        addDocStyle(doc);
//        replaceTasks(doc);

        globalReplace(doc);


        return doc.toString()
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;lt;", "<")
                .replaceAll("&amp;gt;", ">");
//                .replaceAll("&amp;amp;lt;", "<")
//                .replaceAll("&amp;amp;gt;", ">");
//                .replaceAll("&lt;br /&gt;", " <br /> ");
//                .replaceAll("&lt;span class=\"tag\"&gt;", "<span class=\"tag\">")
//                .replaceAll("&lt;/span&gt;", "</span>");
    }


    private void globalReplace(Document doc) {
//        Arrays.asList(H1, H2, H3, H4, H5, H6, P, A, LI).forEach(tag -> {
//            for (Element element : doc.getElementsByTag(tag)) {
//                String elementText = element.toString();
//                elementText = parseTasks(elementText); //H1, H2, H3, H4, H5, H6, P, A, LI
//
//                elementText = replace(elementText, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT, 4, 5); //H1, H2, H3, H4, H5, H6
//                elementText = replace(elementText, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT, 3, 4); //P
//
//                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 3, 4); //P
//                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 4, 5); //LI
//                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 3, 4); //A
//
//
//                element.text(elementText);
//            }
//        });

        Arrays.asList(H1, H2, H3, H4, H5, H6).forEach(tag -> {
            for (Element element : doc.getElementsByTag(tag)) {
                String elementText = element.toString();
                System.out.println("ELEMENT: " + elementText);

                elementText = elementText.substring(4, elementText.length() - 5);
                System.out.println("AFTER SUBSTRING: " + elementText);

                elementText = parseTasks(elementText); //H1, H2, H3, H4, H5, H6, P, A, LI
                System.out.println("AFTER PARSE TASKS: " + elementText);

                elementText = replace(elementText, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT); //H1, H2, H3, H4, H5, H6
                System.out.println("AFTER PARSE TAGS: " + elementText);

                element.text(elementText);
            }
        });

        Arrays.asList(P).forEach(tag -> {
            for (Element element : doc.getElementsByTag(tag)) {
                String elementText = element.toString();
                elementText = elementText.substring(3, elementText.length() - 4);
                elementText = parseTasks(elementText); //H1, H2, H3, H4, H5, H6, P, A, LI
                elementText = replace(elementText, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT); //P
                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT); //P
                element.text(elementText);
            }
        });

        Arrays.asList(A).forEach(tag -> {
            for (Element element : doc.getElementsByTag(tag)) {
                String elementText = element.toString();
                elementText = elementText.substring(3, elementText.length() - 4);

                elementText = parseTasks(elementText); //H1, H2, H3, H4, H5, H6, P, A, LI

                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT); //A

                element.text(elementText);
            }
        });

        Arrays.asList(LI).forEach(tag -> {
            for (Element element : doc.getElementsByTag(tag)) {
                String elementText = element.toString();
                elementText = elementText.substring(4, elementText.length() - 5);

                elementText = parseTasks(elementText); //H1, H2, H3, H4, H5, H6, P, A, LI

                //TODO: not replace in case <li>\n<p>
//                elementText = replace(elementText, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT); //LI

                element.text(elementText);
            }
        });

        //TODO: check links
        //TODO: CODE CSS

        

    }

    private String replace(String elementText, String oldString, String newString) {
        return elementText.replaceAll(oldString, newString);
    }


//    private void replaceTasks(Document doc) {
//        Arrays.asList(H1, H2, H3, H4, H5, H6, P, A, LI).forEach(tag -> {
//            for (Element element : doc.getElementsByTag(tag)) {
//                String elementText = element.toString();
//
//
//                element.text(parseTasks(elementText));
//            }
//        });
//    }

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


//
//    /**
//     * A method which parses and replaces hashtags in a doc with a html element.
//     * @param doc a document to parse.
//     */
//    private void replaceHashTags(Document doc) {
//        Arrays.asList(H1, H2, H3, H4, H5, H6)
//                .forEach(header -> replace(doc, header, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT, 4, 5));
//        replace(doc, P, HASH_TAG_REGEX, HASH_TAG_REPLACEMENT, 3, 4);
//    }

//    /**
//     * A method which parses and replaces "\n" in a doc with a <br /> tag.
//     * @param doc a document to parse.
//     */
//    private void replaceNewLines(Document doc) {
//        replace(doc, P, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 3, 4);
//        replace(doc, LI, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 4, 5);
//        replace(doc, A, NEW_LINE_REGEX, NEW_LINE_REPLACEMENT, 3, 4);
//    }

//    /**
//     * A method which replaces strings in a document's elements, and take a substring if required.
//     * @param doc a document to parse.
//     * @param tag a tag to parse by.
//     * @param oldString a string to replace.
//     * @param newString a new string.
//     * @param beginInd an index of the begin of substring.
//     * @param endInd an index of the end of substring.
//     */
//    private void replace(Document doc, String tag, String oldString, String newString, int beginInd, int endInd) {
//        for(Element element : doc.getElementsByTag(tag)) {
//            String elementText = element.toString();
//            elementText = elementText.substring(beginInd, elementText.length() - endInd);
//            element.text(elementText.replaceAll(oldString, newString));
//        }
//    }

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
