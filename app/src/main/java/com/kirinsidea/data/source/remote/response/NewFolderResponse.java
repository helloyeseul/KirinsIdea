package com.kirinsidea.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewFolderResponse {
    @SerializedName("folderName")
    @Expose
    private final String folderName;

    @SerializedName("storagetime")
    @Expose
    private final String storagetime;

    public NewFolderResponse(String folderName, String storagetime) {
        this.folderName = folderName;
        this.storagetime = storagetime;
    }
    public String getFolderName() {return folderName;}

    public String getStoragetime() {
        return storagetime;
    }

    @Override
    public String toString() {
        return "NewFolderResponse{" +
                "folderName='" + folderName + '\'' +
                ", storagetime='" + storagetime + '\'' +
                '}';
    }
}