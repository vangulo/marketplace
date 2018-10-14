package com.marketplace.dao;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;

import java.util.HashMap;

/*
Create a DAO interface so that data store can be easily changed
 */

public interface ProjectDAO {

    Project createProject(BaseProject newProject);

    Project findProjectById(Integer id);

    HashMap<Integer, Project> listProjects();

    Project addBid(Integer projectId, Bid bid);

    void setWiningBid(Project project);

    void setLowestBid(Project project, Bid bid);
}
