package com.girltalkradio.ui.multimedia;

public class MultimediaPost {

    private String creator;
    private String imageUrl;
    private String link;
    private String title;

    public MultimediaPost(){

    }

    public MultimediaPost(String creator, String imageUrl, String link, String title) {
        this.creator = creator;
        this.imageUrl = imageUrl;
        this.link = link;
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }
}
