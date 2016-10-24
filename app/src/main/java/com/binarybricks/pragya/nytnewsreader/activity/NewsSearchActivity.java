package com.binarybricks.pragya.nytnewsreader.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.binarybricks.pragya.nytnewsreader.R;
import com.binarybricks.pragya.nytnewsreader.adapters.NewsArticleAdapter;
import com.binarybricks.pragya.nytnewsreader.fragments.ArticleSearchFilterFragment;
import com.binarybricks.pragya.nytnewsreader.model.ArticleFilterModel;
import com.binarybricks.pragya.nytnewsreader.model.ArticleSearchResponse;
import com.binarybricks.pragya.nytnewsreader.model.Docs;
import com.binarybricks.pragya.nytnewsreader.network.ArticleSearchAPI;
import com.binarybricks.pragya.nytnewsreader.utils.EndlessScrollListener;
import com.binarybricks.pragya.nytnewsreader.utils.ItemClickSupport;
import com.binarybricks.pragya.nytnewsreader.utils.SpacesItemDecoration;
import com.binarybricks.pragya.nytnewsreader.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSearchActivity extends AppCompatActivity implements ArticleSearchFilterFragment.OnCompleteListener {

    public static final String NEWS_DESK = "news_desk:(";
    @BindView(R.id.rvNewsArticle)
    RecyclerView rvNewsArticle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArrayList<Docs> articleDocsList;
    private NewsArticleAdapter newsArticleAdapter;
    private ArticleFilterModel articleFilterModel;
    private EndlessScrollListener endlessScrollListener;

    private Integer pageNo = 0;
    private String query = null;

    private final int requestCodeCustomTab = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorIconText));

        articleDocsList = new ArrayList<>();
        newsArticleAdapter = new NewsArticleAdapter(articleDocsList, this);

        initializeRecyclerView();
        rvNewsArticle.setAdapter(newsArticleAdapter);

        loadNewsArticles(true);
    }

    private void initializeRecyclerView()
    {
        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        // Attach the layout manager to the recycler view
        rvNewsArticle.setLayoutManager(gridLayoutManager);
        rvNewsArticle.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        rvNewsArticle.addItemDecoration(decoration);

        // Retain an instance so that you can call `resetState()` for fresh searches
        endlessScrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                pageNo++;
                loadNewsArticles(false);
            }
        };

        rvNewsArticle.addOnScrollListener(endlessScrollListener);

        //ClickHandlers for clicking recycler view items
        ItemClickSupport.addTo(rvNewsArticle).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                openCustomTab(position);
            }
        });
    }

    private void fetchNewsArticles(String query,String beginDate, String sortOrder, String filterQuery, Integer page_no, final boolean refreshArticleList) {

        if(refreshArticleList)
        {
            rvNewsArticle.setVisibility(View.GONE);
        }

        ArticleSearchAPI.getArticlesResponse(new Callback<ArticleSearchResponse>() {
            @Override
            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
                if (response.isSuccessful()) {
                    ArticleSearchResponse articleSearchResponse = response.body();
                    articleDocsList = articleSearchResponse.getResponse().getDocs();
                    newsArticleAdapter.setArticleDocsList(articleDocsList, refreshArticleList);
                    newsArticleAdapter.notifyDataSetChanged();
                    rvNewsArticle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
                Toast.makeText(NewsSearchActivity.this, R.string.response_failed, Toast.LENGTH_SHORT).show();
            }
        },query, beginDate, sortOrder, filterQuery, page_no);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSubmitButtonEnabled(true);
        // Use a custom search icon for the SearchView in AppBar
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.search_btn);

        // Customize searchview text and hint colors
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.WHITE);
        et.setHintTextColor(Color.LTGRAY);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                loadNewsArticles(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                query = null;
                loadNewsArticles(true);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_menu) {
            showFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onComplete(ArticleFilterModel articleFilterModel) {
        pageNo = 0;
        this.articleFilterModel = articleFilterModel;
        loadNewsArticles(true);
    }

    private void showFilterDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArticleSearchFilterFragment fragment = ArticleSearchFilterFragment.newInstance(articleFilterModel);
        fragment.show(fragmentManager, "");
    }

    private void loadNewsArticles(Boolean refreshArticles) {

        if(Utils.isNetworkAvailable(NewsSearchActivity.this)) {
            //create fq query here
            if (articleFilterModel != null) {
                String filterQuery = null;
                ArrayList<String> newsDeskValue = articleFilterModel.getNewsDeskValue();
                if (!newsDeskValue.isEmpty()) {
                    filterQuery = NEWS_DESK;
                    for (String newsDesk : newsDeskValue) {
                        filterQuery = filterQuery + "\"" + newsDesk + "\"";
                    }
                    filterQuery = filterQuery + ")";
                }
                fetchNewsArticles(query, articleFilterModel.getBeginDate(), articleFilterModel.getSortOrder(), filterQuery, pageNo, refreshArticles);
            } else {
                fetchNewsArticles(query, null, null, null, pageNo, refreshArticles);
            }
        }
        else {
            Snackbar.make(rvNewsArticle, R.string.no_internet_msg,Snackbar.LENGTH_LONG).show();
        }
    }

    private void openCustomTab(int position) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(NewsSearchActivity.this, R.color.colorPrimary));
        Intent intent = new Intent(Intent.ACTION_SEND);

        PendingIntent pendingIntent = PendingIntent.getActivity(NewsSearchActivity.this,
                requestCodeCustomTab, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_share);

        builder.setActionButton(bitmap, getString(R.string.share_link), pendingIntent, true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(NewsSearchActivity.this, Uri.parse(newsArticleAdapter.getItemAtPosition(position).getWeb_url()));
    }
}
