package com.cooper.nwemail.ui.news.detail;

import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.ui.common.BasePresenter;

/**
 * TODO
 */
public abstract class NewsDetailPresenter extends BasePresenter<NewsDetailView> {

    public NewsDetailPresenter(NewsDetailView view) {
        super(view);
    }

    abstract void setArticleId(final String articleId);

    abstract Article loadArticle(final String articleId);

    abstract String getArticleURL();
}
