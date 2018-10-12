package com.marketplace.health;

import com.codahale.metrics.health.HealthCheck;
import com.marketplace.dao.ProjectDAO;

public class DataSourceHealthCheck extends HealthCheck {
    private final ProjectDAO dao;

    public DataSourceHealthCheck(ProjectDAO dao) {
        this.dao = dao;
    }

    @Override
    protected Result check(){
        if (dao.listProjects() != null) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Cannot connect to data source");
        }
    }
}