package com.example.newsarticleapp.ui.savednews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsarticleapp.R;
import com.example.newsarticleapp.adapter.NewsSavedAdapter;
import com.example.newsarticleapp.database_handler.DatabaseHandler;
import com.example.newsarticleapp.models.NewsArticleModel;

import java.util.ArrayList;

public class SavedArticleFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Context context;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        databaseHandler = new DatabaseHandler(context);
        initViews();
        loadNews();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    LinearLayout noSavedNews;
    TextView title;
    private void initViews() {
        //getting the toolbar
        recyclerView = root.findViewById(R.id.newsArticle);
        progressBar = root.findViewById(R.id.progress_bar);
        noSavedNews = root.findViewById(R.id.noInternetLayout);
        title = root.findViewById(R.id.title);
        title.setText(R.string.no_saved_news);
    }

    private ArrayList<NewsArticleModel> newsArticleModelArrayList;

    private void loadNews() {
        newsArticleModelArrayList = databaseHandler.fetchNewsArticle();

        if (newsArticleModelArrayList.isEmpty()) {
            noSavedNews.setVisibility(View.VISIBLE);
        } else {
            noSavedNews.setVisibility(View.GONE);
            loadNewsArticle();
        }

    }

    NewsSavedAdapter newsAdapter;

    private void loadNewsArticle() {
        newsAdapter = new NewsSavedAdapter(newsArticleModelArrayList, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);

    }
}
