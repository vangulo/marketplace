package com.marketplace.core;

import com.marketplace.api.Bid;
import com.marketplace.api.BaseProject;
import com.marketplace.api.Project;
import com.marketplace.dao.ProjectDAO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.HashMap;

public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);
    ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Project findProjectById(Integer id) {
        Project project = projectDAO.findProjectById(id);
        validateProjectId(id, project);
        if(isNotActive(project) && project.getWinningBid() == null){
            projectDAO.setWiningBid(project);
        }
        return project;
    }

    private void validateProjectId(Integer id, Project project) {
        if (project == null) {
            throw new WebApplicationException(
                    MessageFormat.format("projectId {0} not found.", id), Response.Status.NOT_FOUND);
        }
    }


    public Integer createProject(BaseProject newProject) {
        validateNewProject(newProject);
        Integer id = projectDAO.createProject(newProject);
        return id;
    }

    private void validateNewProject(BaseProject newProject) {
        if (newProject.getMaxBudget() <= 0) {
            throw new WebApplicationException("maxBudget must be greater than 0.", Response.Status.BAD_REQUEST);
        }

        if (isNotActive(newProject)) {
            throw new WebApplicationException("Project deadline is in the past.", Response.Status.BAD_REQUEST);
        }
    }

    public HashMap<Integer, Project> listProjects() {
        return projectDAO.listProjects();
    }

    public Project addBid(Integer projectId, Bid bid) {
        Project project = findProjectById(projectId);

        validateBid(bid, project);

        projectDAO.addBid(projectId, bid);
        projectDAO.setLowestBid(project, bid);
        return project;
    }

    private void validateBid(Bid bid, Project project) {
        if (project.getLowestBid() != null && project.getLowestBid() <= bid.getPrice()){
            throw new WebApplicationException(
                    MessageFormat.format("Bid price is higher than current lowestPrice of {0}.", project.getLowestBid()), Response.Status.BAD_REQUEST);
        }
        if (bid.getPrice() > project.getMaxBudget() ){
            throw new WebApplicationException(
                    MessageFormat.format("Bid price is higher than max budget of {0}.", project.getMaxBudget()), Response.Status.BAD_REQUEST);
        }

        if (isNotActive(project)) {
            throw new WebApplicationException("Project deadline has passed, bids no longer being accepted.", Response.Status.BAD_REQUEST);
        }
    }

    private boolean isNotActive(BaseProject project) {
        if (project.getDeadline().isBefore(DateTime.now())){
            return true;
        }
        return false;
    }
}
