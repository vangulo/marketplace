package com.marketplace.resources;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.marketplace.api.BaseProject;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ProjectTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        DateTime dateTime = new DateTime(2019, 01, 01, 15, 28, 56);

        final BaseProject baseProject = new BaseProject(
                "this is a project",
                1100.00,
                dateTime);

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/BaseProject.json"), BaseProject.class));

        assertThat(MAPPER.writeValueAsString(baseProject)).isEqualTo(expected);
    }
}
