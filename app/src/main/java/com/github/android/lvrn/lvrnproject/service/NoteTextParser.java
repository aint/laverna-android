package com.github.android.lvrn.lvrnproject.service;

import android.support.v4.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.regex.Pattern;

import io.reactivex.Flowable;


import static io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteTextParser {
    private static final String TAG_REGEX = "#[^#]+";

    private static final String TASK_INCOMPLETE_REGEX = "\\[\\] .+";

    private static final String TASK_COMPLETE_REGEX = "\\[X\\] .+";

    /**
     * A method which parse an input text for tags, if any is present.
     *
     * @param text a String object which contain content of a note.
     * @return a set of tags in string form.
     */
    public static Set<String> parseTags(String text) {
        text = text == null ? "" : text;
        Set<String> tagsSet = new HashSet<>();
        Flowable.fromArray(text.split("\n"))
                .flatMap(line -> Flowable.fromArray(line.split(" ")))
                .map(String::trim)
                .filter(word -> Pattern.matches(TAG_REGEX, word))
                .subscribe(tagsSet::add);
        return tagsSet;
    }

    /**
     * A method which parse an input text for task, if any is present.
     *
     * @param text a String object which contain content of a note.
     * @return a set of tasks in string form.
     */
    public static Map<String, Boolean> parseTasks(String text) {
        text = text == null ? "" : text;
        Map<String, Boolean> tasksMap = new HashMap<>();
        Flowable<String> flowable = getTextFlowable(text);
        Flowable.merge(getTaskUncompletedFlowable(flowable), getTaskCompletedFlowable(flowable))
                .subscribe(pair -> tasksMap.put(pair.first, pair.second));
        return tasksMap;
    }

    /**
     * A method which creates a flowable from input text's lines.
     *
     * @param text a String object which contain content of a note.
     * @return {@link Flowable<String>}
     */
    private static Flowable<String> getTextFlowable(String text) {
        return Flowable.fromArray(text.split("\n"))
                .map(String::trim)
                .share();
    }

    /**
     * A method which creates a flowable which emits a {@link Pair<String, Boolean>} of uncompleted
     * tasks.
     *
     * @param flowable a {@link Flowable<String>} object which emits lines of a note text.
     * @return {@link Flowable<String>}
     */
    private static Flowable<Pair<String, Boolean>> getTaskUncompletedFlowable(Flowable<String> flowable) {
        return flowable.filter(line -> Pattern.matches(TASK_INCOMPLETE_REGEX, line))
                .map(line -> new Pair<>(line.substring(3), false));
    }

    /**
     * A method which creates a flowable which emits a {@code Pair<String, Boolean>} of completed
     * tasks.
     *
     * @param flowable a {@link Flowable<String>} object which emits lines of a note text.
     * @return {@link Flowable<String>}
     */
    private static Flowable<Pair<String, Boolean>> getTaskCompletedFlowable(Flowable<String> flowable) {
        return flowable.filter(line -> Pattern.matches(TASK_COMPLETE_REGEX, line))
                .map(line -> new Pair<>(line.substring(4), true));
    }
}
