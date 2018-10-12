package com.marketplace.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel(value = "Bid")
public class Bid implements Comparable<Bid> {
    @ApiParam(required = true)
    private String buyerName;
    @ApiParam(required = true)
    private Double price;

    public Bid(){}

    public Bid(String buyerName, Double price) {
        this.buyerName = buyerName;
        this.price = price;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Bid otherBid) {
        double value = this.price - otherBid.price;
        if (value < 0){
            return -1;
        } else {
            return 1;
        }
    }
}
