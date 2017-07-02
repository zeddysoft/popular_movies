package com.zeddysoft.popularmovies.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.adapters.ReviewAdapter;
import com.zeddysoft.popularmovies.adapters.TrailerAdapter;
import com.zeddysoft.popularmovies.apis.ApiManager;
import com.zeddysoft.popularmovies.models.Review;
import com.zeddysoft.popularmovies.parsers.ReviewParser;
import com.zeddysoft.popularmovies.parsers.TrailerParser;

import org.json.JSONException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment implements ApiManager.MovieApiCallback{


    private RecyclerView reviewRV;
    private ProgressBar loadingBar;
    private ApiManager apiManager;
    private List<Review> reviews;

    public ReviewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiManager.getApiManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        reviewRV = (RecyclerView) view.findViewById(R.id.reviewRV);
        loadingBar = (ProgressBar) view.findViewById(R.id.review_loading_bar);

        long movieId = getArguments().getLong(getString(R.string.review_data_key));
        showReviews(movieId);
        return view;
    }

    private void showReviews(long movieId) {
        apiManager.fetchReviews(this, movieId);
    }


    @Override
    public void onResponse(String response) {
        Log.d("responses", response);
        loadingBar.setVisibility(View.GONE);

        loadingBar.setVisibility(View.GONE);
        try {
            reviews = ReviewParser.parseReviewResponse(response);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            reviewRV.setLayoutManager(mLayoutManager);
            reviewRV.setItemAnimator(new DefaultItemAnimator());
            reviewRV.setAdapter(new ReviewAdapter(getActivity(), reviews));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        loadingBar.setVisibility(View.GONE);
    }
}