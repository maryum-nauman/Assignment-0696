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

        movieList.add(new Movie("Avengers Doomsday", "Sci-Fi/Action", R.drawable.avengers,
                "https://www.youtube.com/watch?v=399Ez7WHK5s", true));

        movieList.add(new Movie("Spider-Man: Brand New Day", "Adventure/Sci-Fi", R.drawable.spiderman,
                "https://www.youtube.com/watch?v=8TZMtslA3UY", true));

        movieList.add(new Movie("Dune: Part 3", "Sci-Fi/Adventure", R.drawable.dune,
                "https://www.youtube.com/watch?v=3_9vCamtuPY", true));

        movieList.add(new Movie("Scary Movie 6", "Horror/Comedy", R.drawable.scarymovie,
                "https://www.youtube.com/watch?v=wUVjdquQAxU", true));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieAdapter(getContext(), movieList));
    }
}
