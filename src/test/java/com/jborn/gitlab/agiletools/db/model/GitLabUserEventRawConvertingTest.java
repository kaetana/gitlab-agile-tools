package com.jborn.gitlab.agiletools.db.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class GitLabUserEventRawConvertingTest {
    GitLabUserEvent userEvent;

    @Before
    public void setUp() throws Exception {
        userEvent = new GitLabUserEvent();
    }

    @Test
    public void convertToEmptyObject() throws Exception {
        userEvent.setRaw("{}");

        ObjectNode objectNode = userEvent.convertRaw();

        assertThat(objectNode.size(), is(0));
    }

    @Test
    public void convert() throws Exception {
        userEvent.setRaw("{\"field\": \"value\"}");

        ObjectNode objectNode = userEvent.convertRaw();

        assertThat(objectNode.size(), is(1));
        assertThat(objectNode.get("field").asText(), is("value"));
    }

    @Test
    public void convertFailed() throws Exception {
        userEvent.setRaw("{\"field\"}");

        ObjectNode objectNode = userEvent.convertRaw();

        assertNull(objectNode);
    }
}