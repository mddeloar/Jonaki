package com.example.mddeloarhossain.jonaki;

import com.google.firebase.database.Exclude;

/**
 * Created by MD. DELOAR HOSSAIN on 27-Jun-19.
 */

public class AdapterBloodDonorStatusSet {
    private String donationstatus;
    private String tutionstatus;
    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
    public AdapterBloodDonorStatusSet(){

    }

    public AdapterBloodDonorStatusSet(String donationstatus, String tutionstatus) {
        this.donationstatus = donationstatus;
        this.tutionstatus = tutionstatus;
    }

    public String getDonationstatus() {
        return donationstatus;
    }

    public void setDonationstatus(String donationstatus) {
        this.donationstatus = donationstatus;
    }

    public String getTutionstatus() {
        return tutionstatus;
    }

    public void setTutionstatus(String tutionstatus) {
        this.tutionstatus = tutionstatus;
    }
}
