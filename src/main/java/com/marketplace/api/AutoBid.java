package com.marketplace.api;

public class AutoBid extends Bid {

    public Double getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(Double maxBid) {
        this.maxBid = maxBid;
    }

    private Double maxBid;

}
