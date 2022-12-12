package com.mobileapp.app;

public class BillCard {

    private String billTitle;
    private String billAmount;
    private String billDate;


    //Setters
    BillCard(String billTitle,String billAmount,String billDate) {

        this.billTitle = billTitle;
        this.billAmount = billAmount;
        this.billDate = billDate;

    }


    //Getters

    public String getBillTitle() {
        return billTitle;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public String getBillDate() {
        return billDate;
    }


}
