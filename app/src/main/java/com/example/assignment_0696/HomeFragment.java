package com.example.assignment_0696;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        ImageButton btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(requireContext(), btnMenu);
            popupMenu.inflate(R.menu.menu_home);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.viewBooking) {

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser == null) {
                        Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    String userId = currentUser.getUid();
                    SharedPreferences prefs =
                            requireContext().getSharedPreferences("BookingPrefs", Context.MODE_PRIVATE);

                    String movie = prefs.getString(userId + "_movie", null);

                    if (movie == null) {
                        Toast.makeText(getContext(),
                                "No previous booking found",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        int seats     = prefs.getInt(userId + "_seats", 0);
                        float total   = prefs.getFloat(userId + "_total", 0);

                        new AlertDialog.Builder(requireContext())
                                .setTitle("Last Booking")
                                .setMessage(
                                        "Movie: " + movie +
                                                "\nSeats: " + seats +
                                                "\nTotal Price: $" + total
                                )
                                .setPositiveButton("OK", null)
                                .show();
                    }
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }
}