package com.example.newsarticleapp.interfaces;


import com.example.newsarticleapp.models.PublisherModel;

import java.util.ArrayList;

public interface OnPublisherSelection {
    public void OnPublisherSelected(ArrayList<PublisherModel> publisherModel, ArrayList<PublisherModel> selectedAll);
}
