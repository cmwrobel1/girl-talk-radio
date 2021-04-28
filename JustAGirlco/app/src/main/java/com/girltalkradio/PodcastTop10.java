package com.girltalkradio;

public class PodcastTop10 {

    private String rss;
    private String title;
    private Long order;
    private String image;

    public PodcastTop10(){

    }

    public PodcastTop10(String url, String title, Long order, String image){
        this.rss = url;
        this.title = title;
        this.order = order;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }
    public String getRss(){
        return rss;
    }
    public Long getOrder(){
        return order + 1;
    }
    public String getImage(){return image;}
    public void setImage(String image){this.image = image;}
    public void setTitle(String title) {this.title = title;}
    public void setRss(String rss){this.rss = rss;}
    public void setOrder(Long order){this.order = order;}

}
