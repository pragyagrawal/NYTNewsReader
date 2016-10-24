package com.binarybricks.pragya.nytnewsreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.binarybricks.pragya.nytnewsreader.R;
import com.binarybricks.pragya.nytnewsreader.model.Docs;
import com.binarybricks.pragya.nytnewsreader.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public class NewsArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Docs> articleDocsList;
    private Context context;

    private final int HEADLINE_ONLY = 0, HEADLINE_THUMBNAIL = 1;

    public NewsArticleAdapter(List<Docs> articleDocsList, Context context) {
        this.articleDocsList = articleDocsList;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Return a new holder instance
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case HEADLINE_THUMBNAIL:
                //Inflate the custom layout
                View articleView = inflater.inflate(R.layout.news_article_item, parent, false);
                //Return a new holder instance
                viewHolder = new ViewHolderThumbnail(articleView);
                break;
            case HEADLINE_ONLY:
                //Inflate the custom layout
                View articleViewHeadlineOnly = inflater.inflate(R.layout.news_article_item_healdine_only, parent, false);
                //Return a new holder instance
                viewHolder = new ViewHolderHeadlineOnly(articleViewHeadlineOnly);
                break;
            default:
                View v = inflater.inflate(R.layout.news_article_item, parent, false);
                viewHolder = new ViewHolderThumbnail(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADLINE_THUMBNAIL:
                ViewHolderThumbnail viewHolderThumbnail = (ViewHolderThumbnail) holder;
                configureViewHolderThumbnail(viewHolderThumbnail, position);
                break;
            case HEADLINE_ONLY:
                ViewHolderHeadlineOnly viewHolderHeadlineOnly = (ViewHolderHeadlineOnly) holder;
                configureViewHolderHeadlineOnly(viewHolderHeadlineOnly, position);
                break;
            default:
                ViewHolderThumbnail vh = (ViewHolderThumbnail) holder;
                configureViewHolderThumbnail(vh, position);
                break;
        }
    }

    private void configureViewHolderHeadlineOnly(ViewHolderHeadlineOnly viewHolderHeadlineOnly, int position) {
        // Get the data model based on position
        Docs articleDocs = articleDocsList.get(position);

        // Set item views based on your views and data model
        viewHolderHeadlineOnly.tvHeadlinesOnly.setText(articleDocs.getHeadline().getMain());
    }

    private void configureViewHolderThumbnail(ViewHolderThumbnail viewHolderThumbnail, int position) {
        // Get the data model based on position
        Docs articleDocs = articleDocsList.get(position);

        // Set item views based on your views and data model
        viewHolderThumbnail.tvHeadlines.setText(articleDocs.getHeadline().getMain());
        if (!articleDocs.getMultimedia().isEmpty()) {
            viewHolderThumbnail.ivThumbnail.setHeightRatio((Double.parseDouble(articleDocs.getMultimedia().get(0).getHeight()) / Double.parseDouble(articleDocs.getMultimedia().get(0).getWidth())));
            Glide.with(context).load(Constants.IMAGE_BASE_URL + articleDocs.getMultimedia().get(0).getUrl())
                    .placeholder(R.drawable.thumbnail_placeholder).error(R.drawable.error_image).into(viewHolderThumbnail.ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return articleDocsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (articleDocsList.get(position).getMultimedia().isEmpty()) {
            return HEADLINE_ONLY;
        } else {
            return HEADLINE_THUMBNAIL;
        }
    }

    public void setArticleDocsList(List<Docs> newArticleDocsList, boolean refreshArticleList) {
        if (articleDocsList == null || refreshArticleList) {
            articleDocsList = newArticleDocsList;
        } else {
            articleDocsList.addAll(newArticleDocsList);
        }
    }

    public Docs getItemAtPosition(int position) {
        return articleDocsList.get(position);
    }
}
