package com.anselmo.encuentrapareja.model;

/**
 * Created by naranya on 9/1/15.
 */
public class Step {
    private int resourceImage;
    private String title;
    private String subtitle;

    public Step() {}

    public Step(int resourceImage, String title, String subtitle) {
        this.resourceImage = resourceImage;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getResourceImage() { return resourceImage; }
    public void setResourceImage(int resourceImage) { this.resourceImage = resourceImage; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}
