package com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl

import com.github.android.lvrn.lvrnproject.view.util.consts.CHECKBOX_CHECKED_TAG
import com.github.android.lvrn.lvrnproject.view.util.consts.CHECKBOX_UNCHECKED_TAG
import com.github.android.lvrn.lvrnproject.view.util.consts.GT1_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.GT2_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.GT3_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.GT_SYMBOL
import com.github.android.lvrn.lvrnproject.view.util.consts.HASH_TAG_REGEX
import com.github.android.lvrn.lvrnproject.view.util.consts.HASH_TAG_REPLACEMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.LINE_WITH_TASK_REGEX
import com.github.android.lvrn.lvrnproject.view.util.consts.LT1_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.LT2_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.LT3_CODE
import com.github.android.lvrn.lvrnproject.view.util.consts.LT_SYMBOL
import com.github.android.lvrn.lvrnproject.view.util.consts.NEW_LINE_IN_LI_REGEX
import com.github.android.lvrn.lvrnproject.view.util.consts.NEW_LINE_REGEX
import com.github.android.lvrn.lvrnproject.view.util.consts.NEW_LINE_REPLACEMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TABLE_TAG
import com.github.android.lvrn.lvrnproject.view.util.consts.TABLE_WRAP_DIV_TAG
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_A
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H1
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H2
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H3
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H4
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H5
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_H6
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_LI
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_P
import com.github.android.lvrn.lvrnproject.view.util.consts.TASK_REGEX
import com.github.android.lvrn.lvrnproject.view.util.consts.TASK_X_BIG_MARK
import com.github.android.lvrn.lvrnproject.view.util.consts.TASK_X_SMALL_MARK
import com.github.android.lvrn.lvrnproject.view.util.consts.WebViewStyleConst.DOC_STYLE
import com.github.android.lvrn.lvrnproject.view.util.consts.WebViewStyleConst.HIGHLIGHT_JS_SCRIPT
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.commonmark.Extension
import org.commonmark.ext.autolink.AutolinkExtension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.Arrays
import java.util.function.Consumer
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class MarkdownParserImpl @Inject constructor() : MarkdownParser {
    private val parser: Parser

    private val renderer: HtmlRenderer

    init {
        parser = getParser()
        renderer = htmlRender
    }

    override fun getParsedHtmlFlow(text: String): Flow<String> {
        return flow {
            Logger.d("Raw text:\n %s", text)
            var textHtml = parseMarkdown(text)
            textHtml = additionalReplaces(textHtml)
            Logger.d("After parse:\n %s", textHtml)
        }
    }

    override fun getParsedHtml(text: String): String {
        Logger.d("Raw text:\n %s", text)
        var textHtml = parseMarkdown(text)
        textHtml = additionalReplaces(textHtml)
        Logger.d("After parse:\n %s", textHtml)
        return textHtml
    }

    /**
     * A method which parses a text with markdown using a commonmark's parser.
     * @param text a text to parse.
     * @return a text after parsing.
     */
    private fun parseMarkdown(text: String): String {
        val node = parser.parse(text)
        return renderer.render(node)
    }

    /**
     * A method parses html using Jsoup, and makes additional replacements.
     * @param htmlText a text to parse.
     * @return a text with replacements.
     */
    private fun additionalReplaces(htmlText: String): String {
        val doc = getParsedHtmlDocument(htmlText)
        makeTablesResponsive(doc)
        replaceInHeaders(doc)
        replaceInListItems(doc)
        replaceInParagraphs(doc)
        replaceInHyperLinks(doc)
        return replaceGtAndLt(doc)
    }

    /**
     * A method which parses html using Jsoup,
     * @param htmlText a text to parse.
     * @return a document with parsed text.
     */
    private fun getParsedHtmlDocument(htmlText: String): Document {
        val doc = Jsoup.parseBodyFragment(htmlText)
        doc.outputSettings(Document.OutputSettings().prettyPrint(false))
        doc.head().append(DOC_STYLE)
        doc.body().append(HIGHLIGHT_JS_SCRIPT)
        return doc
    }

    /**
     * A method which wraps a table element with div blocks to make table responsive.
     * @param doc a document to parse.
     */
    private fun makeTablesResponsive(doc: Document) {
        for (element in doc.getElementsByTag(TABLE_TAG)) {
            element.wrap(TABLE_WRAP_DIV_TAG)
        }
    }

    /**
     * A method which replaces hash tags and tasks with html elements in the document's headers.
     * @param doc a document to parse.
     */
    private fun replaceInHeaders(doc: Document) {
        Arrays.asList<String>(TAG_H1, TAG_H2, TAG_H3, TAG_H4, TAG_H5, TAG_H6).forEach(
            Consumer<String> { tag: String? ->
                for (element in doc.getElementsByTag(tag)) {
                    var elementText = element.toString()
                    elementText = elementText.substring(4, elementText.length - 5)
                    elementText = replaceTasks(elementText)
                    elementText =
                        elementText.replace(HASH_TAG_REGEX.toRegex(), HASH_TAG_REPLACEMENT)
                    element.text(elementText)
                }
            })
    }

    /**
     * A method which replaces hash tags, tasks and new lines with html elements in the document's
     * paragraphs.
     * @param doc a document to parse.
     */
    private fun replaceInParagraphs(doc: Document) {
        for (element in doc.getElementsByTag(TAG_P)) {
            var elementText = element.toString()
            elementText = elementText.substring(3, elementText.length - 4)
            elementText = replaceTasks(elementText)
            elementText = elementText.replace(HASH_TAG_REGEX.toRegex(), HASH_TAG_REPLACEMENT)
            elementText = elementText.replace(NEW_LINE_REGEX.toRegex(), NEW_LINE_REPLACEMENT)
            element.text(elementText)
        }
    }

    /**
     * A method which replaces tasks and new lines with html elements in the document's hyperlinks.
     * @param doc a document to parse.
     */
    private fun replaceInHyperLinks(doc: Document) {
        for (element in doc.getElementsByTag(TAG_A)) {
            var elementText = element.toString()
            elementText = elementText.substring(3, elementText.length - 4)
            elementText = replaceTasks(elementText)
            elementText = elementText.replace(NEW_LINE_REGEX.toRegex(), NEW_LINE_REPLACEMENT)
            element.text(elementText)
        }
    }

    /**
     * A method which replaces tasks and new lines with html elements in the document's hyperlinks.
     * @param doc a document to parse.
     */
    private fun replaceInListItems(doc: Document) {
        for (element in doc.getElementsByTag(TAG_LI)) {
            var elementText = element.toString()
            elementText = elementText.substring(4, elementText.length - 5)
            elementText = replaceTasks(elementText)
            elementText = elementText.replace(HASH_TAG_REGEX.toRegex(), HASH_TAG_REPLACEMENT)
            elementText = elementText.replace(NEW_LINE_IN_LI_REGEX.toRegex(), NEW_LINE_REPLACEMENT)
            element.text(elementText)
        }
    }

    /**
     * A method which finds and replaces task's brackets to checkboxes.
     * @param text a text to parse.
     * @return a text with replacements.
     */
    private fun replaceTasks(text: String): String {
        var text = text
        val lineWithTaskMatcher = Pattern.compile(LINE_WITH_TASK_REGEX).matcher(text)
        while (lineWithTaskMatcher.find()) {
            val lineWithTask = lineWithTaskMatcher.group()
            val pureTaskMatcher = Pattern.compile(TASK_REGEX).matcher(lineWithTask)
            if (pureTaskMatcher.find()) {
                text = text.replace(
                    lineWithTask,
                    replaceBracketsWithCheckboxTag(pureTaskMatcher.group(0))
                )
                continue
            }
            Logger.wtf("Parser filtered line with a task normally, but couldn't find task then")
            throw RuntimeException()
        }
        return text
    }

    /**
     * A method which replaces brackets with checkbox tags.
     * @param task a string which contains brackets.
     * @return a string with replaced brackets.
     */
    private fun replaceBracketsWithCheckboxTag(task: String): String {
        val mark = task[1].toString()
        var checkbox: String = CHECKBOX_UNCHECKED_TAG
        if (mark == TASK_X_SMALL_MARK || mark == TASK_X_BIG_MARK) {
            checkbox = CHECKBOX_CHECKED_TAG
        }
        return checkbox + task.substring(3).trim { it <= ' ' }
    }

    /**
     * A method which replace all lt and gt symbols.
     * @param doc a document to parse.
     * @return a parsed document.
     */
    private fun replaceGtAndLt(doc: Document): String {
        return doc.toString()
            .replace(LT1_CODE.toRegex(), LT_SYMBOL)
            .replace(GT1_CODE.toRegex(), GT_SYMBOL)
            .replace(LT2_CODE.toRegex(), LT_SYMBOL)
            .replace(GT2_CODE.toRegex(), GT_SYMBOL)
            .replace(LT3_CODE.toRegex(), LT_SYMBOL)
            .replace(GT3_CODE.toRegex(), GT_SYMBOL)
    }

    /**
     * A method which adds style in the head of the document.
     * @param doc a document to add style.
     */
    private fun addDocStyle(doc: Document) {
        val head = doc.head()
        head.append(DOC_STYLE)
    }

    /**
     * A method which returns Parser which parses input text to a tree of nodes.
     * @return a Parser object.
     */
    private fun getParser(): Parser {
        return Parser.builder()
            .extensions(extensions)
            .build()
    }

    private val htmlRender: HtmlRenderer
        /**
         * A method which returns HtmlRender which renders a tree of nodes to HTML.
         * @return a HtmlRender object.
         */
        get() = HtmlRenderer.builder()
            .extensions(extensions)
            .build()

    private val extensions: List<Extension>
        /**
         * A method which returns a list of Extension objects for Parser and a HtmlRender.
         * @return a list of extensions.
         */
        get() = Arrays.asList(
            TablesExtension.create(),
            AutolinkExtension.create(),
            StrikethroughExtension.create()
        )

    companion object {
        private const val TAG = "MarkdownParserImpl"
    }
}
