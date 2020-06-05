package com.example.ecommerceproject.Model;

public class Sellers {

    private String sid, sname, sphone,semail, spassword, simage, saddress;

    public Sellers(){}

    public Sellers(String sname,String sid, String sphone, String semail, String spassword, String simage, String saddress) {
        this.sname = sname;
        this.sphone = sphone;
        this.semail = semail;
        this.spassword = spassword;
        this.simage = simage;
        this.saddress = saddress;
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSphone() {
        return sphone;
    }

    public void setSphone(String sphone) {
        this.sphone = sphone;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }
}
