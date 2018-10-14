package com.marketplace.resources;

import com.marketplace.api.Bid;
import com.marketplace.api.BaseProject;
import com.marketplace.api.Project;
import com.marketplace.core.ProjectService;

import io.dropwizard.jersey.params.IntParam;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/api/v1/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value="/projects", description="Operations on Projects in MarketPlace for Self Employed Software Engineers")
public class ProjectResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectResource.class);
    private ProjectService projectService;

    public ProjectResource(ProjectService projectService){
        this.projectService = projectService;
    }

    //curl -X POST 'http://localhost:8080/api/v1/projects' -H 'Content-Type: application/json' --data '{"description": "bar1", "maxBudget": "1100.00", "deadline":"2014-01-01 23:28:56"}'
    @POST
    @ApiOperation(value="Creates a Project", notes="Returns the newly created project")
    @ApiResponses(value={
            @ApiResponse(code=400, message="maxBudget must be greater than 0"),
            @ApiResponse(code=400, message="Project deadline is in the past.")
    })
    public Response createProject(BaseProject baseProject){
        Project project = projectService.createProject(baseProject);
        return Response.ok(project).build();
    }

    @GET
    public HashMap<Integer,Project> listProjects(){
        return projectService.listProjects();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value="Retrieves a Project by Id")
    @ApiResponses(value={
            @ApiResponse(code=400, message="projectId not found.")
    })
    public Response findProjectById(@PathParam("id") IntParam id){
        Project project = projectService.findProjectById(id.get());
        return Response.ok(project).build();
    }

    @POST
    @Path("/{id}/bids")
    @ApiOperation(value="Adds bid to project")
    @ApiResponses(value={
            @ApiResponse(code=400, message="projectId not found."),
            @ApiResponse(code=400, message="Bid price is higher than current lowestPrice."),
            @ApiResponse(code=400, message="Bid price is higher than max budget."),
            @ApiResponse(code=400, message="Project deadline has passed, bids no longer being accepted.")
    })
    public Response addBid(@PathParam("id") IntParam id, Bid bid){
        Project project = projectService.addBid(id.get(), bid);
        return Response.ok(project).build();
    }
}
