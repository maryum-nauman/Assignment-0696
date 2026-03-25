package com.example.assignment_0696;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

public class TicketSummaryFragment extends Fragment {

    private ArrayList<String> selectedSeatsList;

    public TicketSummaryFragment() {
        super(R.layout.fragment_ticket);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Bundle args = getArguments();

        String moviename = args.getString("movie_name");
        int seatCount = args.getInt("seatCount");
        selectedSeatsList = args.getStringArrayList("selected_seats");
        String date = args.getString("date");
        String time = args.getString("time");
        String hall = args.getString("hallno");

        int pc = args.getInt("popcorn", 0);
        int nachos = args.getInt("nachos", 0);
        int sd = args.getInt("softdrink", 0);
        int cm = args.getInt("candymix", 0);

        ImageButton btnBack = view.findViewById(R.id.btnbackticket);
        Button btnSend = view.findViewById(R.id.btnSend);

        TextView tvDate = view.findViewById(R.id.tvDate);
        TextView tvTime = view.findViewById(R.id.tvTime);
        TextView tvHall = view.findViewById(R.id.tvHall);
        TextView tvmoviename = view.findViewById(R.id.tvticketname);
        ImageView ivmovie = view.findViewById(R.id.ticketimage);
        TextView tvSummaryList = view.findViewById(R.id.tvSummaryList);
        TextView tvTotalValue = view.findViewById(R.id.tvTotalValue);

        tvDate.setText(date);
        tvTime.setText(time);
        tvHall.setText(hall);
        tvmoviename.setText(moviename);

        if (moviename.equals("The Dark Knight")) {
            ivmovie.setImageResource(R.drawable.thedarkknight);
        } else if (moviename.equals("Inception")) {
            ivmovie.setImageResource(R.drawable.inception);
        } else if (moviename.equals("Interstellar")) {
            ivmovie.setImageResource(R.drawable.interstellar);
        } else if (moviename.equals("The Shawshank Redemption")) {
            ivmovie.setImageResource(R.drawable.theshawshankredemption);
        }

        double total = 0;
        double ticketPrice = 16.0;

        StringBuilder summary = new StringBuilder("Tickets\n");

        if (selectedSeatsList != null) {
            for (String seat : selectedSeatsList) {
                summary.append(seat).append("   16 USD\n");
                total += ticketPrice;
            }
        }

        summary.append("\nSnacks\n");

        if (pc > 0) {
            summary.append("Popcorn x").append(pc).append(" = ").append(pc * 8.99).append("\n");
            total += pc * 8.99;
        }

        if (nachos > 0) {
            summary.append("Nachos x").append(nachos).append(" = ").append(nachos * 7.99).append("\n");
            total += nachos * 7.99;
        }

        if (sd > 0) {
            summary.append("SoftDrink x").append(sd).append(" = ").append(sd * 5.99).append("\n");
            total += sd * 5.99;
        }

        if (cm > 0) {
            summary.append("CandyMix x").append(cm).append(" = ").append(cm * 6.99).append("\n");
            total += cm * 6.99;
        }

        tvSummaryList.setText(summary.toString());
        tvTotalValue.setText(String.format(Locale.getDefault(), "%.2f USD", total));

        SharedPreferences prefs = requireActivity().getSharedPreferences("BookingPrefs", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("movie", moviename);
        editor.putInt("seats", seatCount);
        editor.putFloat("total", (float) total);

        editor.apply();

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        double finalTotal = total;
        btnSend.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Movie: " + moviename +
                            "\nSeats: " + selectedSeatsList +
                            "\nTotal: " + finalTotal);

            startActivity(Intent.createChooser(intent, "Share Ticket"));
        });
    }
}