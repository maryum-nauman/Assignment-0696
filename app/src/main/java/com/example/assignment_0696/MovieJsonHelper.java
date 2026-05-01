package com.example.assignment_0696;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MovieJsonHelper {

    public static ArrayList<Movie> getMovies(Context context, boolean comingSoon) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject root = new JSONObject(json);
            JSONArray array = comingSoon
                    ? root.getJSONArray("coming_soon")
                    : root.getJSONArray("now_showing");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String name       = obj.getString("name");
                String genre      = obj.getString("genre");
                String imageName  = obj.getString("image");
                String trailerUrl = obj.getString("trailerUrl");

                int imageRes = context.getResources().getIdentifier(
                        imageName, "drawable", context.getPackageName()
                );

                movieList.add(new Movie(name, genre, imageRes, trailerUrl, comingSoon));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }
}