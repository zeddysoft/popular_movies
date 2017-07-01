package com.zeddysoft.popularmovies.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.adapters.TrailerAdapter;
import com.zeddysoft.popularmovies.apis.ApiManager;
import com.zeddysoft.popularmovies.models.Trailer;
import com.zeddysoft.popularmovies.parsers.TrailerParser;

import org.json.JSONException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment implements TrailerAdapter.TrailerPlayListener,
        ApiManager.MovieApiCallback  {


    private RecyclerView trailersView;
    private ProgressBar loadingBar;
    private ApiManager apiManager;
    private List<Trailer> trailers;

    public TrailerFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiManager.getApiManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailer, container, false);

        long movieId = getArguments().getLong(getString(R.string.trailer_data_key));
        trailersView = (RecyclerView) view.findViewById(R.id.trailers_RV);
        loadingBar = (ProgressBar) view.findViewById(R.id.loadingBar);
        fetchMovieTrailers(movieId);

        return view;
    }
    private void fetchMovieTrailers(long id) {
        apiManager.fetchMovieTrailers(this, id);
    }

    @Override
    public void onTrailerClicked(Trailer trailer) {
        viewAndPlayTrailer(trailer);
    }

    private void viewAndPlayTrailer(Trailer trailer) {
        String url = "https://www.youtube.com/watch?v=" + trailer.getKey();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    @Override
    public void onResponse(String response) {
        loadingBar.setVisibility(View.GONE);

        loadingBar.setVisibility(View.GONE);
        try {
            trailers = TrailerParser.parseTrailerResponse(response);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            trailersView.setLayoutManager(mLayoutManager);
            trailersView.setItemAnimator(new DefaultItemAnimator());
            trailersView.setAdapter(new TrailerAdapter(getActivity(),trailers, this));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        loadingBar.setVisibility(View.GONE);
    }
}
