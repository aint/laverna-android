package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteTextParserTest {

    private String textExample;

    private Set<String> trueTags;

    private Set<String> falseTags;

    private Map<String, Boolean> trueTasks;

    private Map<String, Boolean> falseTasks;

    @Before
    public void setUp() {
        textExample = "Check tags\n"
                + "============\n"
                + "#test #laverna #android\n"
                + "* this one with tag #this_one\n"
                + "* that one is not a ##tag and this one too #not_a_tag#\n"
                + "* the other one is#not#a#tag\n"
                + "Check tasks\n"
                + "============\n"
                + "[] this is first task and it ends here!\n"
                + "[X] This is second, and you complete it \n"
                + "[]This is not a task.\n"
                + "[][]This is too.\n"
                + "[] Okay, last task.\n"
                + "[X] No, this is the last task.\n";

        trueTags = new HashSet<>(Arrays.asList("#test", "#laverna", "#android", "#this_one"));

        falseTags = new HashSet<>(Arrays.asList("#no_test", "#no_laverna", "#android"));

        trueTasks = new HashMap<String, Boolean>(){{
                put("This is second, and you complete it", true);
                put("this is first task and it ends here!", false);
                put("Okay, last task.", false);
                put("No, this is the last task.", true);
            }
        };

        falseTasks = new HashMap<String, Boolean>(){{
                put("This is second, and you complete it", false);
                put("this is first task and it ends here!", false);
                put("What? No!", true);
                put("I said no!", true);
                put("Alright..", false);
            }
        };
    }

    @Test
    public void parseTags() {
        Set<String> parseResultSet = NoteTextParser.parseTags(textExample);

        assertThat(parseResultSet.size()).isEqualTo(trueTags.size());

        assertThat(parseResultSet.size()).isNotEqualTo(falseTags.size());

        assertThat(parseResultSet).containsAll(trueTags);

        Assert.assertFalse("Sets must not contain same objects",
                parseResultSet.containsAll(falseTags));
    }

    @Test
    public void parseTasks() {
        Map<String, Boolean> parseResultMap = NoteTextParser.parseTasks(textExample);

        assertThat(parseResultMap.size()).isEqualTo(trueTasks.size());

        assertThat(parseResultMap.size()).isNotEqualTo(falseTasks.size());

        assertThat(parseResultMap.toString()).isEqualTo(trueTasks.toString());

        assertThat(parseResultMap.toString()).isNotEqualTo(falseTasks.toString());
    }
}
