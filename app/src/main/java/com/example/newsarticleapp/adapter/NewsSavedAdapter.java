package com.example.newsarticleapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsarticleapp.R;
import com.example.newsarticleapp.WebBrowserActivity;
import com.example.newsarticleapp.database_handler.DatabaseHandler;
import com.example.newsarticleapp.models.NewsArticleModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsSavedAdapter extends RecyclerView.Adapter<NewsSavedAdapter.MyViewHolder> {

    private ArrayList<NewsArticleModel> newsAdapterArrayList;
    private Context context;
    private static DatabaseHandler db;
    ArrayList<NewsArticleModel> compareArrayList;

    public static final String EXTRA_MESSAGE = "com.example.newsarticle.URL";

    public NewsSavedAdapter(ArrayList<NewsArticleModel> newsAdapterArrayList, Context context) {
        this.newsAdapterArrayList = newsAdapterArrayList;
        this.context = context;
        db = new DatabaseHandler(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_saved_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final NewsArticleModel newsArticleModel = newsAdapterArrayList.get(position);
        holder.title.setText(newsArticleModel.getTitle());
        holder.shortDescription.setText(newsArticleModel.getDescription());

        Glide.with(context).load(newsArticleModel.getUrlToImage())
                .into(holder.articleImage);

    }


    @Override
    public int getItemCount() {
        return newsAdapterArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, shortDescription, readMoreBtn, saveBtn;
        ImageView articleImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            shortDescription = itemView.findViewById(R.id.shortDescription);
            articleImage = itemView.findViewById(R.id.articleImage);

        }
    }
}
