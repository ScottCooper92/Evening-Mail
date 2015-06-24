package com.cooper.nwemail.bus.events;

import android.view.View;

/**
 * An Object representing an Article Click, lets listeners know what article has been clicked
 */
public class ArticleClickEvent {

    private String guid;
    private boolean hasImage;
    private View transitionView;

    public ArticleClickEvent(final String guid, final boolean hasImage, final View transitionView) {
        this.guid = guid;
        this.hasImage = hasImage;
        this.transitionView = transitionView;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public View getTransitionView() {
        return transitionView;
    }
}
