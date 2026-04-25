package com.example.assignment_0696;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NowShowingFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    public NowShowingFragment() {
        super(R.layout.fragment_movies);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewMovies);

        // Load from JSON instead of hardcoding
        movieList = MovieJsonHelper.getMovies(requireContext(), false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieAdapter(getContext(), movieList));
    }
}