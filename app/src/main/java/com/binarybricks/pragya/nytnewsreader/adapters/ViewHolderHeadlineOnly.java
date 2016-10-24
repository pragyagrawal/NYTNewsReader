package com.binarybricks.pragya.nytnewsreader.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.binarybricks.pragya.nytnewsreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRAGYA on 10/23/2016.
 */

public class ViewHolderHeadlineOnly extends RecyclerView.ViewHolder{

    @BindView(R.id.tvHeadlinesOnly)
    TextView tvHeadlinesOnly;

    public ViewHolderHeadlineOnly(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
