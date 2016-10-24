package com.binarybricks.pragya.nytnewsreader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.binarybricks.pragya.nytnewsreader.R;
import com.binarybricks.pragya.nytnewsreader.utils.DynamicHeightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRAGYA on 10/22/2016.
 */

public class ViewHolderThumbnail extends RecyclerView.ViewHolder{

    @BindView(R.id.ivThumbnail)
    DynamicHeightImageView ivThumbnail;

    @BindView(R.id.tvHeadlines)
    TextView tvHeadlines;

    public ViewHolderThumbnail(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
