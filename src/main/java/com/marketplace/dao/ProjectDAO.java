package com.marketplace.dao;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;
import org.joda.time.DateTime;

import java.util.HashMap;

public interface ProjectDAO {
    Integer createProject(BaseProject newProject);

    Project findProjectById(Integer id);

    HashMap<Integer, Project> listProjects();

    Project addBid(Integer projectId, Bid bid);

    void setWiningBid(Project project);

    void setLowestBid(Project project, Bid bid);
}
