package com.jether.monacoshop.Retrofit;

import com.google.gson.annotations.SerializedName;
import com.jether.monacoshop.Models.ModelAll;
import com.jether.monacoshop.Models.ModelBody;
import com.jether.monacoshop.Models.ModelHair;
import com.jether.monacoshop.Models.ModelLatest;

import java.util.ArrayList;


public class  Users {


    @SerializedName("response")
    private String Response;

    @SerializedName("ID")
    private String UserId;

    @SerializedName("Name")
    private String UserName;

    @SerializedName("Email")
    private String UserEmail;

    @SerializedName("Mobile")
    private String UserMobi;

    @SerializedName("Password")
    private String UserPass;

    @SerializedName("Profile")
    private String UserPro;


    @SerializedName("Location")
    private String UserLo;


    @SerializedName("status")
    private String UserSta;


    @SerializedName("id")
    private String ProID;



    @SerializedName("Cost")
    private String ProCost;



    @SerializedName("oderid")
    private String ProOder;

    @SerializedName("products")
    private ArrayList<ModelAll> productAll;


    @SerializedName("bodycare")
    private ArrayList<ModelBody> productBody;



    @SerializedName("haircare")
    private ArrayList<ModelHair> productHair;


    @SerializedName("latest")
    private ArrayList<ModelLatest> productLate;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserMobi() {
        return UserMobi;
    }

    public void setUserMobi(String userMobi) {
        UserMobi = userMobi;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String userPass) {
        UserPass = userPass;
    }

    public String getUserPro() {
        return UserPro;
    }

    public void setUserPro(String userPro) {
        UserPro = userPro;
    }

    public String getUserLo() {
        return UserLo;
    }

    public void setUserLo(String userLo) {
        UserLo = userLo;
    }

    public String getUserSta() {
        return UserSta;
    }

    public void setUserSta(String userSta) {
        UserSta = userSta;
    }

    public String getProID() {
        return ProID;
    }

    public void setProID(String proID) {
        ProID = proID;
    }

    public String getProCost() {
        return ProCost;
    }

    public void setProCost(String proCost) {
        ProCost = proCost;
    }

    public String getProOder() {
        return ProOder;
    }

    public void setProOder(String proOder) {
        ProOder = proOder;
    }

    public ArrayList<ModelAll> getProductAll() {
        return productAll;
    }

    public void setProductAll(ArrayList<ModelAll> productAll) {
        this.productAll = productAll;
    }

    public ArrayList<ModelBody> getProductBody() {
        return productBody;
    }

    public void setProductBody(ArrayList<ModelBody> productBody) {
        this.productBody = productBody;
    }

    public ArrayList<ModelHair> getProductHair() {
        return productHair;
    }

    public void setProductHair(ArrayList<ModelHair> productHair) {
        this.productHair = productHair;
    }

    public ArrayList<ModelLatest> getProductLate() {
        return productLate;
    }

    public void setProductLate(ArrayList<ModelLatest> productLate) {
        this.productLate = productLate;
    }
}
