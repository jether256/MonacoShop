package com.jether.monacoshop.Models;

public class ModelCartItem {

    String id,pid,name,price,total,quantity;

    public ModelCartItem() {

    }

    public ModelCartItem(String id, String pid, String name, String price, String total, String quantity) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.total = total;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
