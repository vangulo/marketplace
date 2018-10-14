package com.marketplace.dao;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

/*
Only actions related to interacting with HashMaps here.
A similar class to this would be created for another implementation such as a sql db.
 */
public class HashMapProjectDAO implements ProjectDAO {
    private static final Logger LOG = LoggerFactory.getLogger(HashMapProjectDAO.class);
    private final HashMap<Integer, Project> projectHashMap;
    private static AtomicInteger count;

    public HashMapProjectDAO() {
        this.projectHashMap = new HashMap<>();
        this.count = new AtomicInteger(0);
    }

    @Override
    public Project createProject(BaseProject newProject) {
        Integer id = count.incrementAndGet();
        DateTime currentDate = new DateTime();
        Project project = new Project(id,
                newProject.getDescription(),
                newProject.getMaxBudget(),
                currentDate,
                newProject.getDeadline());
        LOG.debug("Project to be saved:" + project.toString());
        projectHashMap.put(id,project);
        LOG.debug("hashmap size: " + projectHashMap.size());
        return project;
    }

    @Override
    public Project findProjectById(Integer id) {
        Project project = projectHashMap.get(id);
        return project;
    }

    @Override
    public HashMap<Integer, Project> listProjects() {
        return projectHashMap;
    }

    @Override
    public Project addBid(Integer projectId, Bid bid) {
        Project project = projectHashMap.get(projectId);
        SortedSet<Bid> bids = project.getBids();
        LOG.debug("bid to be added:" + project.toString());
        bids.add(bid);
        return project;
    }

    @Override
    public void setWiningBid(Project project) {
        project.setWinningBid(project.getBids().first());
    }

    @Override
    public void setLowestBid(Project project, Bid bid) {
        project.setLowestBid(bid.getPrice());
    }
}
