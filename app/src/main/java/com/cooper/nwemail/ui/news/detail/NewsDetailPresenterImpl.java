package com.cooper.nwemail.ui.news.detail;

import com.cooper.nwemail.application.NWEApplication;
import com.cooper.nwemail.constants.Constants;
import com.cooper.nwemail.data.DataManager;
import com.cooper.nwemail.models.Article;

import javax.inject.Inject;

/**
 * Takes care of the logic when displaying a news article in NewsDetailActivity and NewsImageDetailActivity.
 */
public class NewsDetailPresenterImpl extends NewsDetailPresenter {

    protected DataManager mData;

    protected String mArticleId;
    protected Article mArticle;
    protected boolean isPremium;

    @Inject
    public NewsDetailPresenterImpl(final NewsDetailView view, final DataManager data) {
        super(view);
        mData = data;
        isPremium = NWEApplication.isPremium();
    }

    @Override
    void setArticleId(final String articleId) {
        mArticleId = articleId;
        initialize();
    }

    @Override
    public void initialize() {

        view.showLoading();

        if (mArticleId != null && !mArticleId.isEmpty()) {
            mArticle = loadArticle(mArticleId);

            if (mArticle != null) {

                //Set the image
                final String imageUrl = mArticle.getArticleImage();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    view.setArticleImage(imageUrl);

                    //Only show a caption if there's an image
                    final String imageCaption = mArticle.getArticleCaption();
                    if (imageCaption != null && !imageCaption.isEmpty()) {
                        view.setImageCaption(mArticle.getArticleCaption());
                    }
                }

                //Set the Activity Title
                view.setActivityTitle(mArticle.getTitle());

                //Set the headline and text
                view.setArticleHeadline(mArticle.getTitle());

                //Set the subheading
                view.setArticleSubheading(mArticle.getSubheading());

                //Set the article content
                final String firstDescription = mArticle.getDescription();
                final String secondDescription = mArticle.getSecondDescription();
                view.setArticleText1(firstDescription);
                view.setArticleText2(secondDescription);

                //If the length of the article is above the max then show the advert
                if ((firstDescription.length() + secondDescription.length())
                        >= Constants.CONTENT_MAX_AD && !isPremium) {
                    view.showMiddleAdvert();
                }

                //Set pub date
                view.setArticlePubDate(mArticle.getPubDate().toString());

                //Set bottom advert
                if (!isPremium) {
                    view.showBottomAdvert();
                }

                view.hideLoading();
            } else {
                view.displayError();
            }
        }
    }

    @Override
    Article loadArticle(final String articleId) {
        return mData.findArticleById(articleId);
    }

    @Override
    String getArticleURL() {
        String url = "";
        if (mArticle != null) {
            url = mArticle.getLink();
        }
        return url;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
