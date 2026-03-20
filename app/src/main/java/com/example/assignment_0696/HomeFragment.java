package com.example.assignment_0696;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jspecify.annotations.NonNull;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Now Showing");
                    else tab.setText("Coming Soon");
                }).attach();

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.viewBooking) {
            SharedPreferences prefs = requireContext().getSharedPreferences("booking", Context.MODE_PRIVATE);

            String movie = prefs.getString("movie", null);

            if (movie == null) {
                Toast.makeText(getContext(), "No previous booking found", Toast.LENGTH_SHORT).show();
            } else {
                int seats = prefs.getInt("seats", 0);
                int price = prefs.getInt("price", 0);

                new AlertDialog.Builder(getContext())
                        .setTitle("Last Booking")
                        .setMessage("Movie: " + movie + "\nSeats: " + seats + "\nTotal Price: $" + price)
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
        return true;
    }
}