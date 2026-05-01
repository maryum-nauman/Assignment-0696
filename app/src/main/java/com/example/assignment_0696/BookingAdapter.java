package com.example.assignment_0696;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    ArrayList<Booking> bookingList;
    OnCancelClickListener cancelListener;

    public interface OnCancelClickListener {
        void onCancelClick(Booking booking, int position);
    }

    public BookingAdapter(Context context, ArrayList<Booking> bookingList,
                          OnCancelClickListener cancelListener) {
        this.context        = context;
        this.bookingList    = bookingList;
        this.cancelListener = cancelListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvMovieName.setText(booking.getMovieName());
        holder.tvDateTime.setText(booking.getDate() + ", " + booking.getTime());
        holder.tvSeats.setText(booking.getSeats() + " Tickets");

        int imageRes = context.getResources().getIdentifier(
                getImageNameForMovie(booking.getMovieName()),
                "drawable",
                context.getPackageName()
        );
        if (imageRes != 0) {
            holder.ivPoster.setImageResource(imageRes);
        }

        holder.btnCancel.setOnClickListener(v ->
                cancelListener.onCancelClick(booking, position));
    }

    private String getImageNameForMovie(String movieName) {
        ArrayList<Movie> allMovies = new ArrayList<>();
        allMovies.addAll(MovieJsonHelper.getMovies(context, false));
        allMovies.addAll(MovieJsonHelper.getMovies(context, true));

        for (Movie movie : allMovies) {
            if (movie.getName().equals(movieName)) {
                // Get drawable name from resource ID
                return context.getResources().getResourceEntryName(movie.getImage());
            }
        }
        return "";
    }

    @Override
    public int getItemCount() { return bookingList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvMovieName, tvDateTime, tvSeats;
        ImageButton btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster     = itemView.findViewById(R.id.ivBookingPoster);
            tvMovieName  = itemView.findViewById(R.id.tvBookingMovieName);
            tvDateTime   = itemView.findViewById(R.id.tvBookingDateTime);
            tvSeats      = itemView.findViewById(R.id.tvBookingSeats);
            btnCancel    = itemView.findViewById(R.id.btnCancelBooking);
        }
    }
}