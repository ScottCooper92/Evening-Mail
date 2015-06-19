package com.cooper.nwemail.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Represents an Article
 */

@Root(name = "item", strict = false)
public class Article extends RealmObject implements Serializable {

    @Element(required = false)
    private String feedId;

    @Element
    private String title;

    private String subheading;

    @Element
    private String link;

    @Element(required = false)
    private String articleImage;

    @Element(required = false)
    private String articleCaption;

    @Element
    private String description;

    private String secondDescription;

    @Element
    private Date pubDate;

    @PrimaryKey
    @Element
    private String guid;

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public String getArticleCaption() {
        return articleCaption;
    }

    public void setArticleCaption(String articleCaption) {
        this.articleCaption = articleCaption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondDescription() {
        return secondDescription;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
