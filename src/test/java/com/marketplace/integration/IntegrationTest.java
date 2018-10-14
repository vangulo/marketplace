package com.marketplace.integration;


import com.marketplace.MarketplaceApplication;
import com.marketplace.MarketplaceConfiguration;
import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class IntegrationTest {

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-config.yml");


    @ClassRule
    public static final DropwizardAppRule<MarketplaceConfiguration> RULE =
            new DropwizardAppRule<>(MarketplaceApplication.class, CONFIG_PATH);

    @Test
    public void createProjectTest() {
        DateTime deadline = new DateTime(2019, 01, 01, 15, 28, 56);
        BaseProject baseProject = new BaseProject("project description ",100.00, deadline );

        final Response response  = RULE.client().target("http://localhost:8080/api/v1/projects")
                .request().post(Entity.entity(baseProject, MediaType.APPLICATION_JSON_TYPE));

        Project project  = response.readEntity(Project.class);

        assertThat(project.getId()).isNotNull();
        assertThat(project.getDeadline()).isEqualTo(baseProject.getDeadline().toDateTime(DateTimeZone.UTC));
        assertThat(project.getMaxBudget()).isEqualTo(baseProject.getMaxBudget());
    }


    @Test
    public void getProjectTest() {
        DateTime deadline = new DateTime(2019, 01, 01, 15, 28, 56);
        BaseProject baseProject = new BaseProject("project description ",100.00, deadline );

        //add project
        RULE.client().target("http://localhost:8080/api/v1/projects")
                .request().post(Entity.entity(baseProject, MediaType.APPLICATION_JSON_TYPE));

        final Response response  = RULE.client().target("http://localhost:8080/api/v1/projects/1")
                .request().get();

        Project project  = response.readEntity(Project.class);

        assertThat(project.getId()).isNotNull();
        assertThat(project.getDeadline()).isEqualTo(baseProject.getDeadline().toDateTime(DateTimeZone.UTC));
        assertThat(project.getMaxBudget()).isEqualTo(baseProject.getMaxBudget());
    }

    @Test
    public void addBidTest() {
        DateTime deadline = new DateTime(2019, 01, 01, 15, 28, 56);
        BaseProject baseProject = new BaseProject("project description ",100.00, deadline );

        Bid bid = new Bid("Joe", 50.00);

        //add project
        RULE.client().target("http://localhost:8080/api/v1/projects")
                .request().post(Entity.entity(baseProject, MediaType.APPLICATION_JSON_TYPE));

        final Response response  = RULE.client().target("http://localhost:8080/api/v1/projects/1/bids")
                .request().post(Entity.entity(bid, MediaType.APPLICATION_JSON_TYPE));

        Project project  = response.readEntity(Project.class);

        assertThat(project.getId()).isNotNull();
        assertThat(project.getBids().first().getBuyerName()).isEqualTo(bid.getBuyerName());
        assertThat(project.getBids().first().getPrice()).isEqualTo(bid.getPrice());
        assertThat(project.getLowestBid()).isEqualTo(bid.getPrice());
    }



}