package com.mobileapp.app;

public class ElectricityBill {

    private String userEmail;
    private String utilityType;

    private float amount;

    private String date;

    public ElectricityBill(String userEmail,String utilityType, float amount, String date) {
        this.utilityType = utilityType;
        this.amount = amount;
        this.date = date;
        this.userEmail=userEmail;
    }

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
