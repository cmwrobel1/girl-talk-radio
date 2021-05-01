package com.girltalkradio;

public class PodcastAuthor {

        private String rss;
        private String title;
        private Long order;

        public PodcastAuthor(){

        }

        public PodcastAuthor(String url, String title, Long order, String image){
            this.rss = url;
            this.title = title;
            this.order = order;
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
        public void setTitle(String title) {this.title = title;}
        public void setRss(String rss){this.rss = rss;}
        public void setOrder(Long order){this.order = order;}

    }
