package com.example.newsarticleapp.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsarticleapp.R;
import com.example.newsarticleapp.adapter.NewsAdapter;
import com.example.newsarticleapp.dialogs.ActionBottomDialogFragment;
import com.example.newsarticleapp.dialogs.FilterActionDialogFragment;
import com.example.newsarticleapp.interfaces.OnFilterSelection;
import com.example.newsarticleapp.interfaces.OnPublisherSelection;
import com.example.newsarticleapp.models.NewsArticleModel;
import com.example.newsarticleapp.models.PublisherModel;
import com.example.newsarticleapp.utils.CheckInternetConnection;
import com.example.newsarticleapp.utils.RequestHandler;
import com.example.newsarticleapp.utils.TimeUtiliserClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomeFragment extends Fragment implements View.OnClickListener {


    private TextView sortBtn, filterBtn;
    RecyclerView recyclerView;
    String myUrl = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";
    ProgressBar progressBar;
    LinearLayout noInternetLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        loadNews();
        return root;
    }

    private void initViews(View root) {
        sortBtn = root.findViewById(R.id.sortBtn);
        filterBtn = root.findViewById(R.id.filterBtn);
        recyclerView = root.findViewById(R.id.newsArticle);
        noInternetLayout = root.findViewById(R.id.noInternetLayout);
        progressBar = root.findViewById(R.id.progress_bar);

    }

    private Context context;
    private OnFilterSelection onFilterSelection = new OnFilterSelection() {
        @Override
        public boolean OnFilterSelected(boolean isNewToOld) {
            if (isNewToOld) {

                Collections.sort(newsArticleModelArrayList, new Comparator<NewsArticleModel>() {
                    @Override
                    public int compare(NewsArticleModel o1, NewsArticleModel o2) {


                        return TimeUtiliserClass.getMinutes(o1.getPublishedAt().substring(11, 20)) - TimeUtiliserClass.getMinutes(o2.getPublishedAt().substring(11, 20));
                    }
                });
                newsAdapter.notifyDataSetChanged();
            } else {
                Collections.sort(newsArticleModelArrayList, new Comparator<NewsArticleModel>() {
                    @Override
                    public int compare(NewsArticleModel o1, NewsArticleModel o2) {


                        return TimeUtiliserClass.getMinutes(o2.getPublishedAt().substring(11, 20)) - TimeUtiliserClass.getMinutes(o1.getPublishedAt().substring(11, 20));
                    }
                });
                newsAdapter.notifyDataSetChanged();
            }
            return false;
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBtn:
                ActionBottomDialogFragment addPhotoBottomDialogFragment =
                        ActionBottomDialogFragment.newInstance(onFilterSelection);

                addPhotoBottomDialogFragment.show(getChildFragmentManager(),
                        ActionBottomDialogFragment.TAG);

                break;

            case R.id.filterBtn:


                FilterActionDialogFragment filterActionDialogFragment =
                        FilterActionDialogFragment.newInstance(newsArticleModelArrayList, onPublisherSelection, selectedModel);

                filterActionDialogFragment.show(getChildFragmentManager(),
                        "FilterBtnDialog");

                break;
        }
    }

    private void loadNews() {
        if (CheckInternetConnection.checkInternetConnection(context)) {
            new HomeFragment.RequestAsync().execute();
            noInternetLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }

    }

    public class RequestAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet(myUrl);

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                parseResponse(s);
                progressBar.setVisibility(View.GONE);

            }
        }


    }

    private ArrayList<NewsArticleModel> newsArticleModelArrayList = new ArrayList<>();

    private void parseResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject innerJSONObject = jsonArray.getJSONObject(i);
                String source = innerJSONObject.getString("source");
                String author = innerJSONObject.getString("author");
                String title = innerJSONObject.getString("title");
                String description = innerJSONObject.getString("description");
                String url = innerJSONObject.getString("url");
                String urlToImage = innerJSONObject.getString("urlToImage");
                String publishedAt = innerJSONObject.getString("publishedAt");
                String content = innerJSONObject.getString("content");

                NewsArticleModel newsArticleModel = new NewsArticleModel(source, author, title, description, url, urlToImage, publishedAt, content);
                newsArticleModelArrayList.add(newsArticleModel);
            }
            loadNewsArticle();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private NewsAdapter newsAdapter;

    private void loadNewsArticle() {
        sortBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(this);
        newsAdapter = new NewsAdapter(newsArticleModelArrayList, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
    }

    private void loadFilteredArticle() {
        newsAdapter = new NewsAdapter(filteredArrayList, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
    }

    private ArrayList<NewsArticleModel> filteredArrayList = new ArrayList<>();
    private ArrayList<PublisherModel> selectedModel = new ArrayList<>();


    private OnPublisherSelection onPublisherSelection = new OnPublisherSelection() {
        @Override
        public void OnPublisherSelected(ArrayList<PublisherModel> publisherModel, ArrayList<PublisherModel> selectedAll) {
            filteredArrayList.clear();
            selectedModel.clear();
            selectedModel.addAll(selectedAll);

            for (int i = 0; i < publisherModel.size(); i++) {
                PublisherModel publisherModel1 = publisherModel.get(i);
                for (NewsArticleModel newsArticleModel : newsArticleModelArrayList) {
                    if (newsArticleModel.getSource().contains(publisherModel1.getName())) {
                        filteredArrayList.add(newsArticleModel);
                    }

                }
            }
            if (publisherModel.isEmpty()) {
                loadNewsArticle();
            } else {
                loadFilteredArticle();
            }

        }
    };

}
