package com.example.assignment_0696;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.*;

public class SeatSelectionFragment extends Fragment {

    private int selectedCount = 0;
    private ArrayList<String> selectedSeatsList = new ArrayList<>();

    public SeatSelectionFragment() {
        super(R.layout.fragment_seatselection);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        LinearLayout linear3 = view.findViewById(R.id.linear3);
        Button btnBookSeats = view.findViewById(R.id.btnBookTickets);
        Button btnProceed = view.findViewById(R.id.btnProceed);
        ImageButton btnBack = view.findViewById(R.id.btnBack);

        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvTitle = view.findViewById(R.id.tvmoviename);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvHall = view.findViewById(R.id.tvHall);

        Bundle args = getArguments();
        String movieName = args.getString("movie_name");
        String time = args.getString("time");
        String hall = args.getString("hallno");
        boolean isComingSoon = args.getBoolean("isComingSoon");

        tvTitle.setText(movieName);
        tvTime.setText(time);
        tvHall.setText(hall);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String finalDate = sdf.format(calendar.getTime());
        tvDate.setText(finalDate);

        btnBack.setOnClickListener(v ->
                ((MainActivity) requireActivity()).loadFragment(new HomeFragment())
        );

        if (isComingSoon) {

            btnBookSeats.setText("Coming Soon");
            btnBookSeats.setEnabled(false);

            btnProceed.setText("Watch Trailer");
            btnProceed.setEnabled(true);
            btnProceed.setAlpha(1f);

            btnProceed.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com"));
                startActivity(intent);
            });

            // Disable seats
            generateSeats(linear3, false, btnProceed);

            return;
        }

        btnProceed.setEnabled(false);
        btnProceed.setAlpha(0.5f);

        btnBookSeats.setOnClickListener(v -> {
            if (selectedCount > 0) {
                Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("movie_name", movieName);
                bundle.putInt("seatCount", selectedCount);

                Intent intent = new Intent(getActivity(), TicketSummaryFragment.class);

                intent.putStringArrayListExtra("selected_seats", selectedSeatsList);
                intent.putExtra("movie_name", movieName);
                intent.putExtra("seatCount", selectedCount);
                intent.putExtra("time", time);
                intent.putExtra("date", finalDate);
                intent.putExtra("hallno", hall);

                startActivity(intent);

            } else {
                Toast.makeText(getContext(), "Seat Not Selected!", Toast.LENGTH_SHORT).show();
            }
        });

        btnProceed.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("movie_name", movieName);
            bundle.putInt("seatCount", selectedCount);

            Intent intent = new Intent(getActivity(), SnacksandDrinksFragment.class);

            intent.putStringArrayListExtra("selected_seats", selectedSeatsList);
            intent.putExtra("movie_name", movieName);
            intent.putExtra("seatCount", selectedCount);
            intent.putExtra("time", time);
            intent.putExtra("date", finalDate);
            intent.putExtra("hallno", hall);

            startActivity(intent);
        });

        generateSeats(linear3, true, btnProceed);
    }
    private void generateSeats(LinearLayout parent, boolean clickable, Button btnProceed) {

        int seatSize = 80;
        int seatMargin = 10;

        for (int r = 0; r < 8; r++) {

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);

            int cols = (r == 0 || r == 7) ? 6 : 8;

            for (int c = 0; c < cols; c++) {

                ImageButton seat = new ImageButton(getContext());

                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(seatSize, seatSize);
                params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);

                if (r==0 && c == 3) params.leftMargin = 40;
                if (r==7 && c == 3) params.leftMargin = 40;
                if (r==1 && c == 4) params.leftMargin = 40;
                if (r==2 && c == 4) params.leftMargin = 40;
                if (r==3 && c == 4) params.leftMargin = 40;
                if (r==4 && c == 4) params.leftMargin = 40;
                if (r==5 && c == 4) params.leftMargin = 40;
                if (r==6 && c == 4) params.leftMargin = 40;

                seat.setLayoutParams(params);
                seat.setBackgroundResource(R.drawable.seatselector);

                if (!clickable) {
                    seat.setEnabled(false);
                }

                int finalR = r;
                int finalC = c;

                seat.setOnClickListener(v -> {

                    if (!v.isEnabled()) return;

                    boolean selected = !v.isSelected();
                    v.setSelected(selected);

                    String seatName = "R" + finalR + "C" + finalC;

                    if (selected) {
                        selectedCount++;
                        selectedSeatsList.add(seatName);
                    } else {
                        selectedCount--;
                        selectedSeatsList.remove(seatName);
                    }

                    if (selectedCount > 0) {
                        btnProceed.setEnabled(true);
                        btnProceed.setAlpha(1f);
                    } else {
                        btnProceed.setEnabled(false);
                        btnProceed.setAlpha(0.5f);
                    }
                });

                row.addView(seat);
            }

            parent.addView(row);
        }
    }
}