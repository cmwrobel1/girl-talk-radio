package com.girltalkradio;

/**
 * This class will be used to create objects for each Podcast so we can organize how they appear easier.
 * Katie Tooher
 */

public class PodcastData {

    private String title;
    private String image;
    private String author;
    private String mp3;
    private String description;

    public PodcastData(String title, String image, String author, String mp3, String description){
        this.title = title;
        this.image = image;
        this.author = author;
        this.mp3 = mp3;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {

        return image;
    }

    public String getAuthor() {

        return author;
    }
    public String getMp3() {

        return mp3;
    }
    public String getDescription() {

        return description;
    }


}
