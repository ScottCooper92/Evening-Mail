package com.cooper.nwemail.ui.news;

import com.cooper.nwemail.enums.NavigationEnum;
import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.ui.common.BasePresenter;

import io.realm.RealmResults;

public abstract class NewsListPresenter extends BasePresenter<NewsListView> {

    public NewsListPresenter(NewsListView view) {
        super(view);
    }

    abstract void setNewsType(final NavigationEnum newsType);

    abstract RealmResults<Article> loadArticles();

    abstract boolean loadAndShowArticles();

    abstract void updateArticles();
}
