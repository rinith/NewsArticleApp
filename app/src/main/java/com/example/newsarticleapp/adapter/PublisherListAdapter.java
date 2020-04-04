package com.example.newsarticleapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newsarticleapp.R;
import com.example.newsarticleapp.database_handler.DatabaseHandler;
import com.example.newsarticleapp.models.NewsArticleModel;
import com.example.newsarticleapp.models.PublisherModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PublisherListAdapter extends RecyclerView.Adapter<PublisherListAdapter.MyViewHolder> {

    private ArrayList<PublisherModel> newsAdapterArrayList;
    private Context context;
    private static DatabaseHandler db;


    public PublisherListAdapter(ArrayList<PublisherModel> newsAdapterArrayList, Context context) {
        this.newsAdapterArrayList = newsAdapterArrayList;
        this.context = context;
        db = new DatabaseHandler(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_publisher_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final PublisherModel publisherModel = newsAdapterArrayList.get(position);
        holder.title.setText(publisherModel.getName());

        holder.title.setBackground(publisherModel.isChecked() ?ContextCompat.getDrawable(context, R.drawable.oval_bg) : ContextCompat.getDrawable(context, R.drawable.oval_clear));

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publisherModel.setChecked(!publisherModel.isChecked());
                holder.title.setBackground(publisherModel.isChecked() ? ContextCompat.getDrawable(context, R.drawable.oval_bg) : ContextCompat.getDrawable(context, R.drawable.oval_clear));
            }
        });

    }

    public void setEmployees(ArrayList<PublisherModel> publisherModels) {
        this.newsAdapterArrayList = new ArrayList<>();
        this.newsAdapterArrayList = publisherModels;
        notifyDataSetChanged();
    }

    public ArrayList<PublisherModel> getAll() {
        return newsAdapterArrayList;
    }

    public ArrayList<PublisherModel> getSelected() {
        ArrayList<PublisherModel> selected = new ArrayList<>();
        for (int i = 0; i < newsAdapterArrayList.size(); i++) {
            if (newsAdapterArrayList.get(i).isChecked()) {
                selected.add(newsAdapterArrayList.get(i));
            }
        }
        return selected;
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
