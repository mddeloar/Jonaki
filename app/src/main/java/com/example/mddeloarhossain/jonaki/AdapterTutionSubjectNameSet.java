package com.example.mddeloarhossain.jonaki;

import com.google.firebase.database.Exclude;

/**
 * Created by MD. DELOAR HOSSAIN on 27-Jun-19.
 */

public class AdapterTutionSubjectNameSet {
    private String subjecname;
    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
    public AdapterTutionSubjectNameSet(){

    }

    public AdapterTutionSubjectNameSet(String subjecname) {
        this.subjecname = subjecname;
    }

    public String getSubjecname() {
        return subjecname;
    }

    public void setSubjecname(String subjecname) {
        this.subjecname = subjecname;
    }
}
