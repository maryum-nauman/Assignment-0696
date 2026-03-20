package com.example.assignment_0696;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    ArrayList<Movie> list;

    private String getTimeForMovie(String movieName) {
        if (movieName.equals("The Dark Knight")) return "11:30";
        else if (movieName.equals("Inception")) return "1:30";
        else if (movieName.equals("Interstellar")) return "3:30";
        else return "5:30";
    }

    private String getHallForMovie(String movieName) {
        if (movieName.equals("The Dark Knight")) return "1";
        else if (movieName.equals("Inception")) return "2";
        else if (movieName.equals("Interstellar")) return "3";
        else return "4";
    }

    public MovieAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = list.get(position);

        holder.name.setText(movie.getName());
        holder.genre.setText(movie.getGenre());
        holder.image.setImageResource(movie.getImage());

        holder.trailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            context.startActivity(intent);
        });

        holder.book.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("movie_name", movie.getName());
            bundle.putString("time", getTimeForMovie(movie.getName()));
            bundle.putString("hallno", getHallForMovie(movie.getName()));
            bundle.putBoolean("isComingSoon", movie.isComingSoon());

            SeatSelectionFragment fragment = new SeatSelectionFragment();
            fragment.setArguments(bundle);

            ((MainActivity) context).loadFragment(fragment);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, genre;
        Button trailer, book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movieImage);
            name = itemView.findViewById(R.id.movieName);
            genre = itemView.findViewById(R.id.movieGenre);
            trailer = itemView.findViewById(R.id.btnTrailer);
            book = itemView.findViewById(R.id.btnBook);
        }
    }
}
