package com.juno.entities;

public class Company {
    private String name;
    private String logoImage;

    public Company(String name, String logoImage) {
        this.name = name;
        this.logoImage = logoImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}
