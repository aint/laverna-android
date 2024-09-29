package com.github.android.lvrn.lvrnproject.service.util

import android.annotation.SuppressLint
import android.util.Pair
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import java.util.regex.Pattern

private val TAG = "NoteTextParser"

private val TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)"

private val LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+"

private val TASK_REGEX = "\\[(x|X| ?)\\] .*"

private val NEW_LINE_SEPARATOR = "\n"

private val SPACE_SEPARATOR = " "

/**
 * A method which parses content and double single quotes for a correct query.
 * @param content a text of a note.
 * @return parsed content.
 */
fun parseSingleQuotes(content: String): String {
    return content.replace("'".toRegex(), "''")
}

/**
 * A method which parse an input text for tags, if any is present.
 * @param text a String object which contain content of a note.
 * @return a set of tags in string form.
 */
fun parseTags(text: String): Set<String> {
    val tagsSet: MutableSet<String> = HashSet()
    Flowable.fromArray(
        *validateText(text).split(NEW_LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
        .flatMap { line: String ->
            Flowable.fromArray(
                *line.split(SPACE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
        }
        .map<String> { obj: String -> obj.trim { it <= ' ' } }
        .filter { obj: String -> validateTag(obj) }
        .subscribe { e: String -> tagsSet.add(e) }
    Logger.d("Text to parse: %s \nParsed tags: %s", text, tagsSet)
    return tagsSet
}

/**
 * A method which parse an input text for task, if any is present.
 * @param text a String object which contain content of a note.
 * @return a set of tasks in string form.
 */
@SuppressLint("CheckResult")
fun parseTasks(text: String): Map<String, Boolean> {
    val tasksMap: MutableMap<String, Boolean> = HashMap()
    Flowable.fromArray<String>(
        *text.split(NEW_LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
        .filter { line: String? ->
            Pattern.matches(
                LINE_WITH_TASK_REGEX,
                line
            )
        }
        .map<String> { obj: String -> getTask(obj) }
        .map<Pair<String, Boolean>> { obj: String -> segregateTask(obj) }
        .subscribe { pair: Pair<String, Boolean> ->
            tasksMap[pair.first] = pair.second
        }

    Logger.d("Text to parse: %s\nParsed tasks: %s", text, tasksMap)
    return tasksMap
}

/**
 * A method which cuts everything before the task brackets.
 * @param line a line with the task.
 * @return the task text.
 * @throws RuntimeException in case if Parser filtered line with a task normally, but couldn't find task then
 */
private fun getTask(line: String): String {
    val matcher = Pattern.compile(TASK_REGEX).matcher(line)
    if (matcher.find()) {
        return matcher.group(0)
    }
    Logger.wtf("Parser filtered line with a task normally, but couldn't find task then")
    throw RuntimeException()
}

/**
 * A method which segregates tasks on completed and uncompleted.
 * @param task a text with a task.
 * @return a pair of the task text and status.
 */
private fun segregateTask(task: String): Pair<String, Boolean> {
    val mark = task[1].toString()
    return Pair(
        task.substring(3).trim { it <= ' ' },
        mark == "x" || mark == "X"
    )
}

/**
 * A method which initialize a received text as blank string in case of equality to null.
 * @param text a received text.
 * @return a text after validation.
 */
private fun validateText(text: String?): String {
    return if (text == null) {
        ""
    } else text
}

/**
 * A method which validate word for a belonging to tags.
 * @param word a tag to validate.
 * @return a boolean result of validation.
 */
private fun validateTag(word: String): Boolean {
    return Pattern.matches(TAG_REGEX, word)
}