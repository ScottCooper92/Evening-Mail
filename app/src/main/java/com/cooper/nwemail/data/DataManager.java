package com.cooper.nwemail.data;

import android.app.Application;
import android.content.Context;
import android.text.Html;

import com.cooper.nwemail.enums.NavigationEnum;
import com.cooper.nwemail.models.Article;
import com.cooper.nwemail.models.Channel;
import com.cooper.nwemail.models.RSSFeed;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * TODO
 */
public class DataManager {

    private final Context mContext;
    private final Realm mRealm;

    public DataManager(final Application application) {
        mContext = application;
        mRealm = Realm.getInstance(application);
    }

    /**
     * @param rssFeed
     * @param feedId
     */
    public void parseArticles(final RSSFeed rssFeed, final String feedId) {
        mRealm.beginTransaction();

        final Channel channel = rssFeed.getChannel();

        for (Article article : channel.getArticles()) {

            try {
                article.setFeedId(feedId);

                //Set the title again, this time with html formatting
                article.setTitle(Html.fromHtml(article.getTitle()).toString());

                //Firstly, remove the image tags from the description
                final String description = article.getDescription();
                final String newDescription = description.replaceAll("<(\\s*)img[^<>]*>", "");

                //Next we need to extract the first subheading from the description and remove any line breaks
                final int subheadingSplit = 4 + newDescription.indexOf("</p>");
                article.setSubheading(Html.fromHtml(
                        newDescription.substring(0, subheadingSplit)).toString().replace("\n", ""));

                //This is the new description minus the subheading
                final String remainingDescription = newDescription.substring(subheadingSplit);

                //Now we split the description in two so that we can position it before and after the advert
                //Find the midpoint
                final int midPoint = remainingDescription.length() / 2;

                //Split the string into two temporary strings
                final String firstString = remainingDescription.substring(0, midPoint);
                final String secondString = remainingDescription.substring(midPoint);

                //Find the closest <p> tag to the midpoint
                final int firstSplit = firstString.lastIndexOf("<p>");
                final int secondSplit = 4 + secondString.indexOf("</p>");

                //Check if either found anything
                if (firstSplit <= 0 || secondSplit <= 4) {
                    //Don't split
                    article.setDescription(Html.fromHtml(remainingDescription).toString());
                } else {
                    //Which one is the least?
                    final int splitPoint = firstSplit < secondSplit ? firstSplit : midPoint + secondSplit;
                    final String firstDescription = Html.fromHtml(remainingDescription.substring(0, splitPoint)).toString();
                    final String secondDescription = Html.fromHtml(remainingDescription.substring(splitPoint)).toString();

                    article.setDescription(firstDescription.trim());
                    article.setSecondDescription(secondDescription.trim());
                }

                mRealm.copyToRealmOrUpdate(article);
            } catch (final Exception e) {
                //If we encounter an exception then skip this article
            }
        }

        mRealm.commitTransaction();
    }

    /**
     * @param articleId
     * @return
     */
    public Article findArticleById(final String articleId) {
        // Build the query to find the article with the given id
        final RealmQuery<Article> query = mRealm.where(Article.class);
        query.equalTo("guid", articleId);

        // Execute the query:
        return query.findFirst();
    }

    /**
     * @param mNewsType
     * @return
     */
    public RealmResults<Article> findAllArticles(final NavigationEnum mNewsType) {
        // Build the query looking at all articles:
        final RealmQuery<Article> query = mRealm.where(Article.class);
        query.equalTo("feedId", NavigationEnum.getFeedId(mNewsType));

        // Execute the query:
        return query.findAllSorted("pubDate", false);
    }


    /**
     *
     */
    public void removeAllRecords() {
        mRealm.beginTransaction();
        mRealm.clear(Article.class);
        mRealm.commitTransaction();
    }


}
