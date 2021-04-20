package com.girltalkradio.ui.multimedia;

public class MultimediaPost {

    private String imageUrl;
    private int order;
    private String text;
    private String user;

    public MultimediaPost(){

    }

    public MultimediaPost(String imageUrl, int order, String text, String user) {
        this.imageUrl = imageUrl;
        this.order = order;
        this.text = text;
        this.user = user;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getOrder() {
        return order;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }
}
