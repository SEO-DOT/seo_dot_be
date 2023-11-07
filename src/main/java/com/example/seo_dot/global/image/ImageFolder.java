package com.example.seo_dot.global.image;

public enum ImageFolder {

    PROFILE("profile");

    private final String folderName;

    ImageFolder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
