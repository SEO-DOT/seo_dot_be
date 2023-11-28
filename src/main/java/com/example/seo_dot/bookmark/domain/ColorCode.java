package com.example.seo_dot.bookmark.domain;

import java.util.Random;

public enum ColorCode {
    D2D2D2, CFCADE, C1C6D9, C1D5D9, ABC1B4, DDCCCE, F3EFE6, A09D92;

    private String colorCode;

    public String getColorCode() {
        return colorCode;
    }
    public static String random() {
        ColorCode[] values = values();
        int length = values.length;
        Random random = new Random();
        int randomIndex = random.nextInt(length);
        return "#" + values[randomIndex];
    }
}
