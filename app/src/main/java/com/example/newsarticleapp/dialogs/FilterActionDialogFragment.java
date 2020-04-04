package com.example.newsarticleapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsarticleapp.R;
import com.example.newsarticleapp.adapter.NewsSavedAdapter;
import com.example.newsarticleapp.adapter.PublisherListAdapter;
import com.example.newsarticleapp.database_handler.DatabaseHandler;
import com.example.newsarticleapp.interfaces.OnFilterSelection;
import com.example.newsarticleapp.interfaces.OnPublisherSelection;
import com.example.newsarticleapp.models.NewsArticleModel;
import com.example.newsarticleapp.models.PublisherModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterActionDialogFragment extends BottomSheetDialogFragment {

    RadioGroup radioGroup;
    private static OnPublisherSelection onPublisherSelections;
    private ArrayList<NewsArticleModel> newsArticleModelArrayList;
    private ArrayList<PublisherModel> selectedModel;

    public static FilterActionDialogFragment newInstance(ArrayList<NewsArticleModel> newsArticleModelArrayList,
                                                         OnPublisherSelection onPublisherSelection, ArrayList<PublisherModel> selectedModel) {
        onPublisherSelections = onPublisherSelection;
        Bundle args = new Bundle();
        args.putParcelableArrayList("newsArticleModelArrayList", newsArticleModelArrayList);
        args.putParcelableArrayList("selectedModel", selectedModel);
        FilterActionDialogFragment fragment = new FilterActionDialogFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsArticleModelArrayList = getArguments().getParcelableArrayList("newsArticleModelArrayList");
            selectedModel = getArguments().getParcelableArrayList("selectedModel");
        }
    }

    private TextView doneBtn;
    private RecyclerView publisherListRecyclerView;
    DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        databaseHandler = new DatabaseHandler(context);

        publisherListRecyclerView = view.findViewById(R.id.publisherListRecyclerView);
        doneBtn = view.findViewById(R.id.doneBtn);

        loadPublisher();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPublisherSelections.OnPublisherSelected(publisherListAdapter.getSelected(),publisherListAdapter.getAll());
                dismiss();
            }
        });
        return view;
    }

    ArrayList<String> publisherList = new ArrayList<>();
    ArrayList<PublisherModel> publisherModelArrayList = new ArrayList<>();
    PublisherListAdapter publisherListAdapter;

    private void loadPublisher() {
        for (NewsArticleModel newsArticleModel : newsArticleModelArrayList) {
            try {
                JSONObject jsonObject = new JSONObject(newsArticleModel.getSource());
                String name = jsonObject.getString("name");
                if (!publisherList.contains(name))
                    publisherList.add(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (String name : publisherList) {
            publisherModelArrayList.add(new PublisherModel(name));
        }

        publisherListAdapter = new PublisherListAdapter(publisherModelArrayList, context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        publisherListRecyclerView.setLayoutManager(mLayoutManager);
        publisherListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        publisherListRecyclerView.setAdapter(publisherListAdapter);
        if (!selectedModel.isEmpty()) {
            publisherListAdapter.setEmployees(selectedModel);
        }


    }


    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }


}
