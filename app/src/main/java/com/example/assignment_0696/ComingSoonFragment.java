package com.example.assignment_0696;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class ComingSoonFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    public ComingSoonFragment() {
        super(R.layout.fragment_movies);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewMovies);

        movieList = new ArrayList<>();

        movieList.add(new Movie("Avatar 3", "Sci-Fi", R.drawable.inception,
                "https://www.youtube.com/watch?v=d9MyW72ELq0", true));

        movieList.add(new Movie("Joker 2", "Drama", R.drawable.interstellar,
                "https://www.youtube.com/watch?v=_OKAwz2MsJs", true));

        movieList.add(new Movie("Dune Part 2", "Sci-Fi", R.drawable.theshawshankredemption,
                "https://www.youtube.com/watch?v=Way9Dexny3w", true));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieAdapter(getContext(), movieList));
    }
}
