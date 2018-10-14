package com.marketplace.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class BaseProject {

    @ApiParam(required = true)
    private String description;
    @ApiParam(required = true)
    private Double maxBudget;
    @ApiParam(required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private DateTime deadline;

    BaseProject(){}

    public BaseProject(String description, Double maxBudget, DateTime deadline) {
        this.description = description;
        this.maxBudget = maxBudget;
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "BaseProject{" +
                "description='" + description + '\'' +
                ", maxBudget=" + maxBudget +
                ", deadline=" + deadline +
                '}';
    }
}
