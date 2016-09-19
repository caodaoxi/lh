package com.lh.modules.trigger.entity;

import com.lh.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by caodaoxi on 16-8-22.
 */
public class Detail extends DataEntity<Detail> {
	private static final long serialVersionUID = 1L;
	private Date dt;
    private long fundid;
    private String stationaddr;
    private String market;
    private String stkcode;
    private double orderprice;
    private double orderqty;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public long getFundid() {
        return fundid;
    }

    public void setFundid(long fundid) {
        this.fundid = fundid;
    }

    public String getStationaddr() {
        return stationaddr;
    }

    public void setStationaddr(String stationaddr) {
        this.stationaddr = stationaddr;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getStkcode() {
        return stkcode;
    }

    public void setStkcode(String stkcode) {
        this.stkcode = stkcode;
    }

    public double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(double orderprice) {
        this.orderprice = orderprice;
    }

    public double getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(double orderqty) {
        this.orderqty = orderqty;
    }
}
