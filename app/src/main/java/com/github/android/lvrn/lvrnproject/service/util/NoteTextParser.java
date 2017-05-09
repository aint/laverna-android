package com.github.android.lvrn.lvrnproject.service.util;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.Flowable;

import static android.content.ContentValues.TAG;
import static java.util.regex.Pattern.matches;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteTextParser {
    private static final String TAG = "NoteTextParser";

    private static final String TAG_REGEX = "(?<=\\s|^)#(\\w*[A-Za-z_]+\\w*)";

    private static final String TASK_INCOMPLETE_REGEX = "\\[\\] .+";

    private static final String TASK_COMPLETE_REGEX = "\\[X\\] .+";

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
     * A method which validate word for a belonging to tags.
     * @param tag a tag to validate.
     * @return a boolean result of validation.
     */
    public static boolean validateTag(@NonNull String word) {
        return matches(TAG_REGEX, word);
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
                .filter(word -> validateTag(word))
                .subscribe(tagsSet::add);
        Log.d(TAG, "Parse tags. Text: " + text + "\nParsed tags:" + tagsSet.toString());
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
        Flowable<String> flowable = getTextFlowable(validateText(text));
        Flowable
                .merge(getTaskUncompletedFlowable(flowable), getTaskCompletedFlowable(flowable))
                .subscribe(pair -> tasksMap.put(pair.first, pair.second));
        Log.d(TAG, "Parse tag. Text: " + text + "\nParsed text:" + tasksMap);
        return tasksMap;
    }

    /**
     * A method which creates a flowable from input text's lines.
     * @param text a String object which contain content of a note.
     * @return {@link Flowable<String>}
     */
    private static Flowable<String> getTextFlowable(@NonNull String text) {
        return Flowable
                .fromArray(text.split(NEW_LINE_SEPARATOR))
                .map(String::trim)
                .share();
    }

    /**
     * A method which creates a flowable which emits a {@link Pair<String, Boolean>} of uncompleted
     * tasks.
     * @param flowable a {@link Flowable<String>} object which emits lines of a note text.
     * @return {@link Flowable<String>}
     */
    private static Flowable<Pair<String, Boolean>> getTaskUncompletedFlowable(@NonNull Flowable<String> flowable) {
        return flowable
                .filter(line -> matches(TASK_INCOMPLETE_REGEX, line))
                .map(line -> new Pair<>(line.substring(3), false));
    }

    /**
     * A method which creates a flowable which emits a {@code Pair<String, Boolean>} of completed
     * tasks.
     * @param flowable a {@link Flowable<String>} object which emits lines of a note text.
     * @return {@link Flowable<String>}
     */
    private static Flowable<Pair<String, Boolean>> getTaskCompletedFlowable(@NonNull Flowable<String> flowable) {
        return flowable
                .filter(line -> matches(TASK_COMPLETE_REGEX, line))
                .map(line -> new Pair<>(line.substring(4), true));
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
}
