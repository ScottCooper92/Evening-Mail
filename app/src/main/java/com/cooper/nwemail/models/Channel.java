package com.cooper.nwemail.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * A POJO Representing an XML Channel
 */
@Root(name = "channel", strict = false)
public class Channel {

    @Element(required = false)
    private String title;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private String link;

    @Element(required = false)
    private String lastBuildDate;

    @ElementList(name = "item", inline = true)
    private List<Article> articles = new ArrayList<>(20);

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
