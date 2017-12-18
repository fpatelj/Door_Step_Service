package com.doorstepservice.darpal.doorstepservice.UserLogin;

/**
 * Created by Darpal on 11/13/2017.
 */

public class ServiceDispGetterSetter {

    private String StoreName;
    private String StoreAddress;
    private String Contact;
    private String Sink;
    private String sinkprice;
    public String Sid;
    private String service;
    private String orderid;
    private String category;

    private String reviews;
    private String reviewer_name;


    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSink() {
        return Sink;
    }

    public void setSink(String sink) {
        Sink = sink;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getReviewer_name() {
        return reviewer_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReviewer_name(String reviewer_name) {
        this.reviewer_name = reviewer_name;
    }

    public String getSinkprice() {
        return sinkprice;
    }

    public void setSinkprice(String sinkprice) {
        this.sinkprice = sinkprice;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
