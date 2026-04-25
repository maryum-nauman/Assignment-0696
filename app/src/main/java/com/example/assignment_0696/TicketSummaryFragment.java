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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TicketSummaryFragment extends Fragment {

    private ArrayList<String> selectedSeatsList;

    public TicketSummaryFragment() {
        super(R.layout.fragment_ticket);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Bundle args = getArguments();
        String moviename      = args.getString("movie_name");
        int seatCount         = args.getInt("seatCount");
        selectedSeatsList     = args.getStringArrayList("selected_seats");
        String date           = args.getString("date");
        String time           = args.getString("time");
        String hall           = args.getString("hallno");

        int pc     = args.getInt("popcorn", 0);
        int nachos = args.getInt("nachos", 0);
        int sd     = args.getInt("softdrink", 0);
        int cm     = args.getInt("candymix", 0);

        ImageButton btnBack   = view.findViewById(R.id.btnbackticket);
        Button btnSend        = view.findViewById(R.id.btnSend);
        TextView tvDate       = view.findViewById(R.id.tvDate);
        TextView tvTime       = view.findViewById(R.id.tvTime);
        TextView tvHall       = view.findViewById(R.id.tvHall);
        TextView tvmoviename  = view.findViewById(R.id.tvticketname);
        ImageView ivmovie     = view.findViewById(R.id.ticketimage);
        TextView tvSummaryList = view.findViewById(R.id.tvSummaryList);
        TextView tvTotalValue  = view.findViewById(R.id.tvTotalValue);

        tvDate.setText(date);
        tvTime.setText(time);
        tvHall.setText(hall);
        tvmoviename.setText(moviename);

        ArrayList<Movie> allMovies = new ArrayList<>();
        allMovies.addAll(MovieJsonHelper.getMovies(requireContext(), false)); // now showing
        allMovies.addAll(MovieJsonHelper.getMovies(requireContext(), true));  // coming soon

        for (Movie movie : allMovies) {
            if (movie.getName().equals(moviename)) {
                ivmovie.setImageResource(movie.getImage());
                break;
            }
        }

        // Calculate total
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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            SharedPreferences prefs = requireActivity().getSharedPreferences("BookingPrefs", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(userId + "_movie", moviename);
            editor.putInt(userId + "_seats", seatCount);
            editor.putFloat(userId + "_total", (float) total);
            editor.apply();
        }
        saveBookingToFirebase(moviename, seatCount, total, date, time);

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

    private void saveBookingToFirebase(String movieName, int seatCount,
                                       double totalPrice, String date, String time) {

        // Get current logged in user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();

        // Reference to: bookings/{userId}/{bookingId}
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(userId);

        // Generate unique booking ID
        String bookingId = bookingsRef.push().getKey();

        // Build booking data map
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("movieName",   movieName);
        bookingData.put("seats",       seatCount);
        bookingData.put("totalPrice",  totalPrice);
        bookingData.put("date",        date);
        bookingData.put("time",        time);
        bookingData.put("dateTime",    date + " " + time);  // combined for easy comparison in My Bookings
        bookingData.put("timestamp",   System.currentTimeMillis()); // for cancellation check in Task 7

        // Store under bookings/{userId}/{bookingId}
        bookingsRef.child(bookingId)
                .setValue(bookingData)
                .addOnSuccessListener(unused ->
                        Toast.makeText(getContext(),
                                "Booking saved successfully!",
                                Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(),
                                "Failed to save booking: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }
}