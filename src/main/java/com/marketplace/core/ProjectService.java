package com.marketplace.core;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;
import com.marketplace.dao.ProjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static com.marketplace.core.Validation.*;

/*
All business logic is here
 */
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

    public Project createProject(BaseProject newProject) {
        validateNewProject(newProject);
        Project project = projectDAO.createProject(newProject);
        return project;
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

}
