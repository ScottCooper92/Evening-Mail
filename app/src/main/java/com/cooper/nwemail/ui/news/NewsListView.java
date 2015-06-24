package com.cooper.nwemail.ui.news;

import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.ui.common.BaseView;

import io.realm.RealmResults;

/**
 * This interface represents the actions that can be performed by a NewsListView
 */
public interface NewsListView extends BaseView {

    void displayArticles(final RealmResults<Article> articles);

    void hideList();

    void showList();

    void hideUpdating();

    void showUpdating();

    void showErrorMessage();
}
