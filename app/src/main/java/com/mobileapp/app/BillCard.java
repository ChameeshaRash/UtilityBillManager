package com.mobileapp.app;

public class BillCard {

    private int billTypeIcon;
    private String billTitle;
    private String billAmount;
    private String billDate;


    //Setters
    BillCard(int billTypeIcon,String billTitle,String billAmount,String billDate) {

        this.billTypeIcon = billTypeIcon;
        this.billTitle = billTitle;
        this.billAmount = billAmount;
        this.billDate = billDate;

    }


    //Getters
    public int getBillTypeIcon() {
        return billTypeIcon;
    }

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
