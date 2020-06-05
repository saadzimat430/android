package com.example.ecommerceproject.Home;

public class Companies
{
    private String cName, cAddress, cOwner, cDescription, cID, cNumber, cProducts, cEmail;

    Companies()
    {

    }

    public Companies(String cName, String cAddress, String cOwner, String cDescription, String cID, String cNumber, String cProducts, String cEmail) {
        this.cName = cName;
        this.cAddress = cAddress;
        this.cOwner = cOwner;
        this.cDescription = cDescription;
        this.cID = cID;
        this.cNumber = cNumber;
        this.cProducts = cProducts;
        this.cEmail = cEmail;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getcOwner() {
        return cOwner;
    }

    public void setcOwner(String cOwner) {
        this.cOwner = cOwner;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getcProducts() {
        return cProducts;
    }

    public void setcProducts(String cProducts) {
        this.cProducts = cProducts;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }
}
