package com.example.sanguineaid.model;

public class Campaign {
    private String name;
    private int imageUrl;
    private String duration;
    private int voucherPercentage;

    public Campaign(String name, int imageUrl, String duration, int voucherPercentage) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.voucherPercentage = voucherPercentage;
    }

    public String getName() {
        return name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getDuration() {
        return duration;
    }

    public int getVoucherPercentage() {
        return voucherPercentage;
    }
}
