package com.marketplace.resources;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Project;
import com.marketplace.core.ProjectService;
import com.marketplace.dao.ProjectDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectResourceTest {

    private static final ProjectService service = mock(ProjectService.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ProjectResource(service))
            .build();

    DateTime dateTime = new DateTime();
    DateTime deadline = dateTime.plusWeeks(1);
    private final Project project = new Project( 1,"blah", 100.00, dateTime, deadline );

    @Before
    public void setup() {
        when(service.findProjectById(eq(1))).thenReturn(project);
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(service);
    }

    @Test
    public void testGetProject() {
        assertThat(resources.target("/api/v1/projects/1").request().get(Project.class))
                .isEqualTo(project);
        verify(service).findProjectById(1);
    }
}