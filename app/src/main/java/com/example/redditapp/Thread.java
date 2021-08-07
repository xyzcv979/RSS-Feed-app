/*
* Grabs content from every thread / post / subreddit
*
 */
package com.example.redditapp;

public class Thread {
    private String title;
    private String author;
    private String dateUpdated;
    private String URL;
    private String thumbnailURL;

    public Thread(String title, String author, String dateUpdated, String URL, String thumbnailURL) {
        this.title = title;
        this.author = author;
        this.dateUpdated = dateUpdated;
        this.URL = URL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
