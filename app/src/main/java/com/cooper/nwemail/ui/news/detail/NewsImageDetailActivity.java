package com.cooper.nwemail.ui.news.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.cooper.nwemail.R;
import com.cooper.nwemail.application.NWEApplication;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.Optional;

/**
 * TODO
 */
public class NewsImageDetailActivity extends NewsDetailActivity implements
        View.OnLongClickListener, View.OnClickListener {

    //ID's
    public static final String IMAGE_VIEW = "IMAGE_VIEW";

    @Optional
    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;

    @Optional
    @InjectView(R.id.imageView_article_detail)
    ImageView mImageView;

    @Optional
    @InjectView(R.id.fab_share)
    FloatingActionButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewCompat.setTransitionName(mImageView, IMAGE_VIEW);

        mImageView.setOnClickListener(this);
        mImageView.setOnLongClickListener(this);

        if (mShareButton != null) {
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareArticle();
                }
            });
        }

        presenter.initialize();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_news_image_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Override this to prevent the share action being added by the parent activity
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);

        if (!NWEApplication.isPremium()) {
            //We still want the upgrade option however
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public void setActivityTitle(String title) {
        if (mCollapseToolbar != null) {
            mCollapseToolbar.setTitle(title);
        } else {
            super.setActivityTitle(title);
        }
    }


    @Override
    public void setArticleImage(String imageUrl) {
        Picasso.with(this).load(imageUrl).into(mImageView);
    }

    @Override
    public void setImageCaption(String caption) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * Static method to start this Activity whilst animating the card title into this title
     */
    public static void launch(final Activity activity, final View transitionView, final String guuid) {
        final ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, IMAGE_VIEW);
        final Intent intent = new Intent(activity, NewsImageDetailActivity.class);
        intent.putExtra(ARG_ARTICLE, guuid);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
