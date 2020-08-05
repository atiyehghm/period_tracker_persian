package edu.sharif.periodtracker.ui.content;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String description;
    private String content;
    private int imgId;

    public Article(String title, String description, String content, int imgId) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
