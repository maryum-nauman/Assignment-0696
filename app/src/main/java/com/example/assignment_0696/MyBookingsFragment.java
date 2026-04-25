package com.example.assignment_0696;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyBookingsFragment extends Fragment {

    RecyclerView rvBookings;
    ArrayList<Booking> bookingList;
    BookingAdapter adapter;
    DatabaseReference bookingsRef;

    public MyBookingsFragment() {
        super(R.layout.fragment_my_booking);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        rvBookings  = view.findViewById(R.id.rvBookings);
        bookingList = new ArrayList<>();

        rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookingAdapter(getContext(), bookingList, this::onCancelClicked);
        rvBookings.setAdapter(adapter);

        loadBookingsFromFirebase();
    }

    private void loadBookingsFromFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String userId = user.getUid();
        bookingsRef = FirebaseDatabase.getInstance()
                .getReference("bookings")
                .child(userId);

        // ✅ Use addListenerForSingleValueEvent to avoid conflict with manual list removal
        bookingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();

                for (DataSnapshot bookingSnap : snapshot.getChildren()) {
                    String bookingId = bookingSnap.getKey();
                    String movieName = bookingSnap.child("movieName").getValue(String.class);
                    String date      = bookingSnap.child("date").getValue(String.class);
                    String time      = bookingSnap.child("time").getValue(String.class);
                    String dateTime  = bookingSnap.child("dateTime").getValue(String.class);

                    // ✅ Safe timestamp read
                    long timestamp = 0;
                    Object tsObj = bookingSnap.child("timestamp").getValue();
                    if (tsObj instanceof Long) {
                        timestamp = (Long) tsObj;
                    }

                    // ✅ Safe seats read
                    int seats = 0;
                    Object seatsObj = bookingSnap.child("seats").getValue();
                    if (seatsObj instanceof Long) {
                        seats = ((Long) seatsObj).intValue();
                    }

                    // ✅ Safe totalPrice read
                    double totalPrice = 0;
                    Object priceObj = bookingSnap.child("totalPrice").getValue();
                    if (priceObj instanceof Double) {
                        totalPrice = (Double) priceObj;
                    } else if (priceObj instanceof Long) {
                        totalPrice = ((Long) priceObj).doubleValue();
                    }

                    Booking booking = new Booking(bookingId, movieName, seats,
                            totalPrice, date, time, dateTime, timestamp);
                    bookingList.add(booking);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),
                        "Failed to load bookings: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCancelClicked(Booking booking, int position) {

        // ✅ Compare only DATE not time (movie showtimes may have passed today)
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date movieDate = sdf.parse(booking.getDate());

            // Strip time from today for fair comparison
            String todayStr = sdf.format(new Date());
            Date today = sdf.parse(todayStr);

            if (movieDate == null || movieDate.before(today)) {
                Toast.makeText(getContext(),
                        "Cannot cancel past bookings",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (Exception e) {
            android.util.Log.e("CANCEL_DEBUG", "Parse error: " + e.getMessage());
            // If date parsing fails, allow cancellation
        }

        // Show confirmation dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) return;

                    FirebaseDatabase.getInstance()
                            .getReference("bookings")
                            .child(user.getUid())
                            .child(booking.getBookingId())
                            .removeValue()
                            .addOnSuccessListener(unused -> {
                                // ✅ Find by bookingId instead of position (position may be stale)
                                for (int i = 0; i < bookingList.size(); i++) {
                                    if (bookingList.get(i).getBookingId()
                                            .equals(booking.getBookingId())) {
                                        bookingList.remove(i);
                                        adapter.notifyItemRemoved(i);
                                        adapter.notifyItemRangeChanged(i, bookingList.size());
                                        break;
                                    }
                                }
                                Toast.makeText(getContext(),
                                        "Booking Cancelled Successfully",
                                        Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(getContext(),
                                            "Failed to cancel: " + e.getMessage(),
                                            Toast.LENGTH_SHORT).show());
                })
                .setNegativeButton("No", null)
                .show();
    }
}