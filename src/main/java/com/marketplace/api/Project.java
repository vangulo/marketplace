package com.marketplace.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import org.joda.time.DateTime;

import java.util.SortedSet;
import java.util.TreeSet;

@ApiModel(value = "Project")
public class Project extends BaseProject {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss")
    private DateTime created;
    private Double lowestBid;
    private Bid winningBid;
    private SortedSet<Bid> bids;

    public Project(){}

    public Project(Integer id, String description, Double maxBudget, DateTime created, DateTime deadline) {
        super(description, maxBudget ,deadline);
        this.id = id;
        this.created = created;
        this.bids = new TreeSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public SortedSet<Bid> getBids() {
        return bids;
    }

    public void setBids(SortedSet<Bid> bids) {
        this.bids = bids;
    }

    public Double getLowestBid() {
        return lowestBid;
    }

    public void setLowestBid(Double lowestBid) {
        this.lowestBid = lowestBid;
    }


    public Bid getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }
}
