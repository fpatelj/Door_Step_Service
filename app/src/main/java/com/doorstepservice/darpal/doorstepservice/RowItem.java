package com.doorstepservice.darpal.doorstepservice;

/**
 * Created by Darpal on 11/2/2017.
 */

public class RowItem {

    private int imageId;
    private String title;
    private String desc;

    public RowItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;

    }

    public RowItem(Integer image, String title) {
        this.imageId = image;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}
