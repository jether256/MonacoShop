package com.jether.monacoshop.Models;

public class ModelAll {

    public ModelAll() {
    }

    private String id,imagee,title,product,descc,price,quant,image,date;
    private int sync_status;

    public ModelAll(String id, String imagee, String title, String product, String descc, String price, String quant, String image, String date, int sync_status) {
        this.id = id;
        this.imagee = imagee;
        this.title = title;
        this.product = product;
        this.descc = descc;
        this.price = price;
        this.quant = quant;
        this.image = image;
        this.date = date;
        this.sync_status = sync_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagee() {
        return imagee;
    }

    public void setImagee(String imagee) {
        this.imagee = imagee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescc() {
        return descc;
    }

    public void setDescc(String descc) {
        this.descc = descc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }
}
