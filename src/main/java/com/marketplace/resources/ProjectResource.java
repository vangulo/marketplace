package com.marketplace.resources;

import com.marketplace.api.Bid;
import com.marketplace.api.BaseProject;
import com.marketplace.api.Project;
import com.marketplace.core.ProjectService;

import io.dropwizard.jersey.params.IntParam;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

/*
Controller logic here to deal with only HTTP interactions
 */
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

    @POST
    @ApiOperation(value="Creates a Project",
            response = Project.class,
            notes="Returns the newly created project")
    @ApiResponses(value={
            @ApiResponse(code=400, message="Request contains invalid parameters")
    })
    public Response createProject(BaseProject baseProject){
        Project project = projectService.createProject(baseProject);
        return Response.ok(project).build();
    }

    @GET
    @ApiOperation(value="For debugging purposes")
    public HashMap<Integer,Project> listProjects(){
        return projectService.listProjects();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value="Retrieves a Project by Id",
            response = Project.class)
    @ApiResponses(value={
            @ApiResponse(code=404, message="projectId not found.")
    })
    public Response findProjectById(@PathParam("id") IntParam id){
        Project project = projectService.findProjectById(id.get());
        return Response.ok(project).build();
    }

    @POST
    @Path("/{id}/bids")
    @ApiOperation(value="Adds bid to project",
            response = Project.class)
    @ApiResponses(value={
            @ApiResponse(code=404, message="projectId not found."),
            @ApiResponse(code=400, message="Request contains invalid parameters")
    })
    public Response addBid(@PathParam("id") IntParam id, Bid bid){
        Project project = projectService.addBid(id.get(), bid);
        return Response.ok(project).build();
    }
}
