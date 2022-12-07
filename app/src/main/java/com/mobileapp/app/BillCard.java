package com.mobileapp.app;

public class BillCard {

    public int billTypeIcon;
    public String billTitle;
    public String billAmount;
    public String billDate;


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

    public void setBillTypeIcon(int billTypeIcon) {
        this.billTypeIcon = billTypeIcon;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
