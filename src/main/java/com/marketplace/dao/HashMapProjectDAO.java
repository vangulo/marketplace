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

public class HashMapProjectDAO implements ProjectDAO {
    private static final Logger LOG = LoggerFactory.getLogger(HashMapProjectDAO.class);
    private final HashMap<Integer, Project> projectHashMap;
    private static AtomicInteger count;


    public HashMapProjectDAO() {
        this.projectHashMap = new HashMap<>();
        this.count = new AtomicInteger(0);
    }

    @Override
    public Integer createProject(BaseProject newProject) {
        Integer id = count.incrementAndGet();
        DateTime currentDate = new DateTime();
        Project p = new Project(id,
                newProject.getDescription(),
                newProject.getMaxBudget(),
                currentDate,
                newProject.getDeadline());
        projectHashMap.put(id,p);
        LOG.debug("hashmap size: " + projectHashMap.size());
        return id;
    }

    public Project findProjectById(Integer id) {
        Project project = projectHashMap.get(id);
        return project;
    }

    public HashMap<Integer, Project> listProjects() {
        return projectHashMap;
    }

    public Project addBid(Integer projectId, Bid bid) {
        Project p = projectHashMap.get(projectId);
        SortedSet<Bid> b = p.getBids();
        b.add(bid);
        return p;
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
