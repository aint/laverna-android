package com.github.android.lvrn.lvrnproject.service.util;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;

import static java.util.regex.Pattern.matches;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteTextParser {
    private static final String TAG = "NoteTextParser";

    private static final String TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";

    private static final String LINE_WITH_TASK_REGEX = ".*\\[(x|X| ?)\\] +.+";

    private static final String TASK_REGEX = "\\[(x|X| ?)\\] .*";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String SPACE_SEPARATOR = " ";

    /**
     * A method which parses content and double single quotes for a correct query.
     * @param content a text of a note.
     * @return parsed content.
     */
    @NonNull
    public static String parseSingleQuotes(String content) {
        return content.replaceAll("'", "''");
    }

    /**
     * A method which parse an input text for tags, if any is present.
     * @param text a String object which contain content of a note.
     * @return a set of tags in string form.
     */
    @NonNull
    public static Set<String> parseTags(String text) {
        Set<String> tagsSet = new HashSet<>();
        Flowable
                .fromArray(validateText(text).split(NEW_LINE_SEPARATOR))
                .flatMap(line -> Flowable.fromArray(line.split(SPACE_SEPARATOR)))
                .map(String::trim)
                .filter(NoteTextParser::validateTag)
                .subscribe(tagsSet::add);
        Logger.d("Text to parse: $s \nParsed tags: $s", text, tagsSet);
        return tagsSet;
    }

    /**
     * A method which parse an input text for task, if any is present.
     * @param text a String object which contain content of a note.
     * @return a set of tasks in string form.
     */
    @NonNull
    public static Map<String, Boolean> parseTasks(String text) {
        Map<String, Boolean> tasksMap = new HashMap<>();
        Flowable.fromArray(text.split(NEW_LINE_SEPARATOR))
                .filter(line -> matches(LINE_WITH_TASK_REGEX, line))
                .map(NoteTextParser::getTask)
                .map(NoteTextParser::segregateTask)
                .subscribe(pair -> tasksMap.put(pair.first, pair.second));

        Logger.d("Text to parse: $s\nParsed tasks: $s", text, tasksMap);
        return tasksMap;
    }

    /**
     * A method which cuts everything before the task brackets.
     * @param line a line with the task.
     * @return the task text.
     * @throws RuntimeException in case if Parser filtered line with a task normally, but couldn't find task then
     */
    private static String getTask(String line) {
        Matcher matcher = Pattern.compile(TASK_REGEX).matcher(line);
        if (matcher.find()) {
            return matcher.group(0);
        }
        Logger.wtf("Parser filtered line with a task normally, but couldn't find task then");
        throw new RuntimeException();
    }

    /**
     * A method which segregates tasks on completed and uncompleted.
     * @param task a text with a task.
     * @return a pair of the task text and status.
     */
    private static Pair<String, Boolean> segregateTask(String task) {
        String mark = Character.toString(task.charAt(1));
        return new Pair<>(task.substring(3).trim(), mark.equals("x") || mark.equals("X"));
    }

    /**
     * A method which initialize a received text as blank string in case of equality to null.
     * @param text a received text.
     * @return a text after validation.
     */
    @NonNull
    private static String validateText(String text) {
        if(text == null){
            text = "";
        }
        return text;
    }

    /**
     * A method which validate word for a belonging to tags.
     * @param word a tag to validate.
     * @return a boolean result of validation.
     */
    private static boolean validateTag(@NonNull String word) {
        return matches(TAG_REGEX, word);
    }
}
