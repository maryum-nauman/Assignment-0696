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
        movieList = new ArrayList<>();
        movieList.add(new Movie("The Dark Knight", "Action | 152 min", R.drawable.thedarkknight,
                "https://www.youtube.com/watch?v=EXeTwQWrcwY", false));
        movieList.add(new Movie("Inception", "Sci-Fi | 148 min", R.drawable.inception,
                "https://www.youtube.com/watch?v=YoHD9XEInc0", false));
        movieList.add(new Movie("Interstellar", "Sci-Fi | 169 min", R.drawable.interstellar,
                "https://www.youtube.com/watch?v=zSWdZVtXT7E", false));
        movieList.add(new Movie("The ShawShank Redemption", "Drama | 142 min", R.drawable.theshawshankredemption,
                "https://www.youtube.com/watch?v=PLl99DlL6b4", false));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MovieAdapter(getContext(), movieList));
    }
}
