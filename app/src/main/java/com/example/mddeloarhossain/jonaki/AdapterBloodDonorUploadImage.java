package com.example.mddeloarhossain.jonaki;

import com.google.firebase.database.Exclude;

/**
 * Created by MD. DELOAR HOSSAIN on 30-Apr-19.
 */

public class AdapterBloodDonorUploadImage {
    private String imageName;
    private String imageUrl;

    private String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public AdapterBloodDonorUploadImage(){

    }

    public AdapterBloodDonorUploadImage(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

