package edu.sharif.periodtracker.ui.content;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String description;
    private String content;
    private String imgName;

    public Article(String title, String description, String content, String imgName) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.imgName = imgName;
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

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
