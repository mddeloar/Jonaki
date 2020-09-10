package com.example.mddeloarhossain.jonaki;

/**
 * Created by MD. DELOAR HOSSAIN on 21-Jun-19.
 */

public class AdapterBloodDonorRegistration {

    private String name;
    private String city;
    private String location;
    private String age;
    private String contactnumber;
    private String bloodgroup;
    private String gender;
    private String donationstatus;

    public AdapterBloodDonorRegistration(){

    }


    public AdapterBloodDonorRegistration(String name, String city, String location, String age, String contactnumber, String bloodgroup, String gender, String donationstatus) {
        this.name = name;
        this.city = city;
        this.location = location;
        this.age = age;
        this.contactnumber = contactnumber;
        this.bloodgroup = bloodgroup;
        this.gender = gender;
        this.donationstatus = donationstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDonationstatus() {
        return donationstatus;
    }

    public void setDonationstatus(String donationstatus) {
        this.donationstatus = donationstatus;
    }
}



