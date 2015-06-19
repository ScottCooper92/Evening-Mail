package com.cooper.nwemail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooper.nwemail.R;
import com.cooper.nwemail.bus.AnyThreadBus;
import com.cooper.nwemail.bus.events.ArticleClickEvent;
import com.cooper.nwemail.models.Article;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * TODO
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ArticleView> {

    final static private int ARTICLE_STANDARD = 1;
    final static private int ARTICLE_THUMBNAIL = 2;

    private Context context;
    private AnyThreadBus mBus;
    private RealmResults<Article> articles;

    public NewsListAdapter(final Context context, final AnyThreadBus bus, final RealmResults<Article> articles) {
        this.context = context;
        mBus = bus;
        this.articles = articles;
    }

    @Override
    public ArticleView onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleView view;
        if (viewType == ARTICLE_STANDARD) {
            view = new ArticleView(LayoutInflater.from(context).inflate(R.layout.card_article, parent, false));
        } else {
            view = new ArticleImageView(LayoutInflater.from(context).inflate(R.layout.card_article_image, parent, false));
        }
        return view;
    }

    @Override
    public void onBindViewHolder(final ArticleView holder, int position) {
        final Article article = articles.get(position);

        final int viewType = holder.getItemViewType();

        holder.title.setText(article.getTitle());
        holder.pubDate.setText(context.getString(R.string.last_updated) + " " + article.getPubDate());

        if (viewType == ARTICLE_THUMBNAIL) {
            final ArticleImageView imageHolder = (ArticleImageView) holder;
            final String imageUrl = article.getArticleImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.with(context).load(imageUrl).into(imageHolder.thumbnail);
            }
            imageHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBus.post(new ArticleClickEvent(article.getGuid(), true, imageHolder.thumbnail));
                }
            });
        } else {

            final String subheading = article.getSubheading();
            if (subheading != null && !subheading.isEmpty()) {
                holder.subheading.setText(subheading);
            }
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String image = article.getArticleImage();
                    mBus.post(new ArticleClickEvent(article.getGuid(), false, null));
                }
            });
        }
    }

    /**
     * Returns a card type based on whether we have an image or not
     *
     * @param position The position in the array
     * @return The type of card
     */
    @Override
    public int getItemViewType(int position) {
        final Article article = articles.get(position);
        final String image = article.getArticleImage();
        return image != null && !image.isEmpty() ? ARTICLE_THUMBNAIL : ARTICLE_STANDARD;
    }

    @Override
    public int getItemCount() {
        if (articles == null) {
            return 0;
        }
        return articles.size();
    }

    public void setArticles(final RealmResults<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }


    static class ArticleView extends RecyclerView.ViewHolder {

        public LinearLayout rootView;
        public TextView title;
        public TextView pubDate;

        private TextView subheading;

        public ArticleView(View itemView) {
            super(itemView);
            rootView = (LinearLayout) itemView.findViewById(R.id.linearLayout_card_root);
            title = (TextView) itemView.findViewById(R.id.textView_article_title);
            subheading = (TextView) itemView.findViewById(R.id.textView_article_subtitle);
            pubDate = (TextView) itemView.findViewById(R.id.textView_article_pubDate);
        }
    }

    static class ArticleImageView extends ArticleView {

        ImageView thumbnail;

        public ArticleImageView(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.imageView_article_thumbnail);
        }
    }
}
