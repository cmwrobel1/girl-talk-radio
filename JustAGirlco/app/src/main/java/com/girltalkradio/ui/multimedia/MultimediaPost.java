package com.girltalkradio.ui.multimedia;

public class MultimediaPost {

    private String creator;
    private String imageUrl;
    private String link;
    private String title;
    private long order;

    public MultimediaPost(){

    }

    public MultimediaPost(String creator, String imageUrl, String link, String title, long order) {
        this.creator = creator;
        this.imageUrl = imageUrl;
        this.link = link;
        this.title = title;
        this.order = order;
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

    public long getOrder() { return order; }
}
