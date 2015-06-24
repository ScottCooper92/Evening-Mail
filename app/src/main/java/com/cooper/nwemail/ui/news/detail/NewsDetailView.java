package com.cooper.nwemail.ui.news.detail;

import com.cooper.nwemail.ui.common.BaseView;

/**
 * This interface represents a NewsDetailView, classes implementing this will be forced to act as a NewsDetailView
 */
public interface NewsDetailView extends BaseView {

    void setActivityTitle(final String title);

    void setArticleHeadline(final String headline);

    void setArticleSubheading(final String subheading);

    void setArticleText1(final String text);

    void setArticleText2(final String text);

    void setArticlePubDate(final String pubDate);

    void setArticleImage(final String imageUrl);

    void setImageCaption(final String caption);

    void showMiddleAdvert();

    void showBottomAdvert();

    void displayError();
}
