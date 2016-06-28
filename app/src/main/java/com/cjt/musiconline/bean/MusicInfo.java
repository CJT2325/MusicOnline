package com.cjt.musiconline.bean;

/**
 * 作者: 陈嘉桐 on 2016/5/28
 * 邮箱: 445263848@qq.com.
 */
public class MusicInfo {
    private String imageUrl;
    private String name;
    private String author;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
