package com.example.mddeloarhossain.jonaki;

import com.google.firebase.database.Exclude;

/**
 * Created by MD. DELOAR HOSSAIN on 27-Jun-19.
 */

public class AdapterLastDonationDateSet {
    private String lastDonationDate;
    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
    public AdapterLastDonationDateSet(){

    }

    public AdapterLastDonationDateSet(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}
