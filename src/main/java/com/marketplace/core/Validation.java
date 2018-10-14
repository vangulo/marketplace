package com.marketplace.core;

import com.marketplace.api.BaseProject;
import com.marketplace.api.Bid;
import com.marketplace.api.Project;
import org.joda.time.DateTime;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;

/*
Placed all Validation here as a static functions to keep Project Service Class Readable
 */
public class Validation {

    public static void validateProjectId(Integer id, Project project) {
        Notification notification = new Notification();
        if (project == null) {
            notification.addError(MessageFormat.format("projectId {0} not found", id));
            throw new WebApplicationException(notification.errorMessage(), Response.Status.NOT_FOUND);
        }
    }

    public static void validateNewProject(BaseProject newProject) {
        Notification notification = new Notification();

        newProjectNullCheck(newProject, notification);

        if (isNotActive(newProject)) {
            notification.addError("Project deadline is in the past");
        }

        if (newProject.getMaxBudget() <= 0) {
            notification.addError("maxBudget must be greater than 0");
        }

        if (notification.hasErrors()) {
            throw new WebApplicationException(notification.errorMessage(), Response.Status.BAD_REQUEST);
        }
    }

    private static void newProjectNullCheck(BaseProject newProject, Notification notification) {
        if (newProject.getDeadline() == null ) {
            notification.addError("Project deadline can not be null");
        }
        if (newProject.getDescription() == null ) {
            notification.addError("Project description can not be null");
        }
        if (newProject.getMaxBudget() == null ) {
            notification.addError("Project maxBudget can not be null");
        }
        if (notification.hasErrors()) {
            throw new WebApplicationException(notification.errorMessage(), Response.Status.BAD_REQUEST);
        }
    }


    public static void validateBid(Bid bid, Project project) {
        Notification notification = new Notification();

        bidNullCheck(bid, notification);

        if (project.getLowestBid() != null && project.getLowestBid() <= bid.getPrice()){
            notification.addError(MessageFormat.format("Bid price is higher than current lowestPrice of {0}", project.getLowestBid()));
        }

        if (bid.getPrice() > project.getMaxBudget()){
            notification.addError(MessageFormat.format("Bid price is higher than max budget of {0}", project.getMaxBudget()));
        }

        if (isNotActive(project)) {
            notification.addError("Project deadline has passed, bids no longer being accepted");
        }
        if (notification.hasErrors()) {
            throw new WebApplicationException(notification.errorMessage(), Response.Status.BAD_REQUEST);
        }
    }

    private static void bidNullCheck(Bid bid, Notification notification) {
        if (bid.getBuyerName() == null ) {
            notification.addError("Bid buyerName can not be null");
        }
        if (bid.getPrice() == null ) {
            notification.addError("Bid price can not be null");
        }
        if (notification.hasErrors()) {
            throw new WebApplicationException(notification.errorMessage(), Response.Status.BAD_REQUEST);
        }
    }

    static boolean isNotActive(BaseProject project) {
        if (project.getDeadline().isBefore(DateTime.now())){
            return true;
        }
        return false;
    }
}
