package com.mobileapp.app;

public class UtilityBill {

    public String utilityType;

    public float amount;

    public String date;

    public  UtilityBill(){}

    public UtilityBill(String utilityType, float amount, String date) {
        this.utilityType = utilityType;
        this.amount = amount;
        this.date = date;
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


}
