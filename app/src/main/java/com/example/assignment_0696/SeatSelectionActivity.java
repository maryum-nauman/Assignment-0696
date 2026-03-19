package com.example.assignment_0696;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SeatSelectionActivity extends AppCompatActivity {
    private int selectedCount = 0;
    private java.util.ArrayList<String> selectedSeatsList = new java.util.ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        TextView tvDate;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatselection);

        LinearLayout linear3 = findViewById(R.id.linear3);
        Button btnBookSeats=findViewById(R.id.btnBookTickets);
        Button btnProceed=findViewById(R.id.btnProceed);
        ImageButton btnback=findViewById(R.id.btnBack);

        btnProceed.setEnabled(false);
        btnProceed.setAlpha(0.5f);

        boolean isToday = getIntent().getBooleanExtra("is_today", true);
        tvDate = findViewById(R.id.tvDate);
        Calendar calendar = Calendar.getInstance();
        if (!isToday) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String finalDate = sdf.format(calendar.getTime());
        tvDate.setText(finalDate);

        String moviename = getIntent().getStringExtra("movie_name");
        TextView tvTitle = findViewById(R.id.tvmoviename);
        tvTitle.setText(moviename);

        String time = getIntent().getStringExtra("time");
        TextView tvTime = findViewById(R.id.tvTime);
        tvTime.setText(time);

        String hall = getIntent().getStringExtra("hallno");
        TextView tvHall = findViewById(R.id.tvHall);
        tvHall.setText(hall);

        btnback.setOnClickListener(v -> finish());
        btnBookSeats.setOnClickListener(v -> {
            if(selectedCount>0) {
                Intent intent = new Intent(SeatSelectionActivity.this, TicketSummary.class);
                intent.putStringArrayListExtra("selected_seats", selectedSeatsList);
                intent.putExtra("movie_name", moviename);
                intent.putExtra("seatCount", selectedCount);
                intent.putExtra("time", time);
                intent.putExtra("date", finalDate);
                intent.putExtra("hallno", hall);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Seat Not Selected!", Toast.LENGTH_SHORT).show();
            }
        });
        btnProceed.setOnClickListener(v -> {
            Intent intent=new Intent(SeatSelectionActivity.this, SnacksandDrinks.class);
            intent.putStringArrayListExtra("selected_seats", selectedSeatsList);
            intent.putExtra("movie_name", moviename);
            intent.putExtra("seatCount",selectedCount);
            intent.putExtra("time",time);
            intent.putExtra("date",finalDate);
            intent.putExtra("hallno",hall);
            startActivity(intent);
        });

        int seatSize = 80; // pixels
        int seatMargin = 10;

        for (int r = 0; r < 8; r++) {
            final int currentRow= r;
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            if(r==0 || r==7)
            {
                for (int c = 0; c < 6; c++) {
                    final int currentCol=c;
                    ImageButton seat = new ImageButton(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(seatSize, seatSize);
                    params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);

                    if (c == 3) params.leftMargin = 40;

                    seat.setLayoutParams(params);
                    seat.setBackgroundResource(R.drawable.seatselector);
                    seat.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                    if (isBooked(moviename,r, c,isToday)) {
                        seat.setEnabled(false);
                    }
                    seat.setOnClickListener(v -> {
                        if (v.isEnabled()) {
                            boolean isNowSelected = !v.isSelected();
                            v.setSelected(isNowSelected);

                            String seatCoord = "Row" + (currentRow + 1) + "Column" + (currentCol + 1);
                            if (isNowSelected) {
                                selectedCount++;
                                selectedSeatsList.add(seatCoord);
                            } else {
                                selectedCount--;
                                selectedSeatsList.remove(seatCoord);
                            }
                            if (selectedCount > 0) {
                                btnProceed.setEnabled(true);
                                btnProceed.setAlpha(1.0f);
                            } else {
                                btnProceed.setEnabled(false);
                                btnProceed.setAlpha(0.5f);
                            }
                        }
                    });
                    row.addView(seat);
                }
                linear3.addView(row);
            }
            else{
                for (int c = 0; c < 8; c++) {
                    final int currentCol=c;
                    ImageButton seat = new ImageButton(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(seatSize, seatSize);
                    params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);

                    if (c == 4) params.leftMargin = 40;

                    seat.setLayoutParams(params);
                    seat.setBackgroundResource(R.drawable.seatselector);
                    seat.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                    if (isBooked(moviename,r, c,isToday)) {
                        seat.setEnabled(false);
                    }
                    seat.setOnClickListener(v -> {
                        if (v.isEnabled()) {
                            boolean isNowSelected = !v.isSelected();
                            v.setSelected(isNowSelected);

                            String seatCoord = "Row" + (currentRow + 1) + "Column" + (currentCol + 1);
                            if (isNowSelected) {
                                selectedCount++;
                                selectedSeatsList.add(seatCoord);
                            } else {
                                selectedCount--;
                                selectedSeatsList.remove(seatCoord);
                            }
                            if (selectedCount > 0) {
                                btnProceed.setEnabled(true);
                                btnProceed.setAlpha(1.0f);
                            } else {
                                btnProceed.setEnabled(false);
                                btnProceed.setAlpha(0.5f);
                            }
                        }
                    });
                    row.addView(seat);
                }
                linear3.addView(row);
            }
        }

    }
    private boolean isBooked(String movie, int row, int col,boolean isToday) {
        if (movie == null) return false;

        if ((movie.equals("The Dark Knight"))&& (isToday==true)) {
            return (row == 1 && col == 0) || (row == 5 && col == 4) || (row == 5 && col == 5);
        } else if ((movie.equals("Inception")) && (isToday==true)) {
            return (row == 0 && col == 2) || (row == 3 && col == 3);
        }else if ((movie.equals("Interstellar")) && (isToday==true)) {
            return (row == 0 && col == 3) || (row == 6 && col == 5);
        }else if ((movie.equals("The Shawshank Redemption")) && (isToday==true)) {
            return (row == 4 && col == 2) || (row == 3 && col == 2);
        }else if ((movie.equals("The Dark Knight"))&& (isToday==false)) {
            return (row == 2 && col == 0) || (row == 6 && col == 3) || (row == 5 && col == 3);
        } else if ((movie.equals("Inception")) && (isToday==false)) {
            return (row == 3 && col == 2) || (row == 3 && col == 3);
        }else if ((movie.equals("Interstellar")) && (isToday==false)) {
            return (row == 3 && col == 3) || (row == 6 && col == 5);
        }else if ((movie.equals("The Shawshank Redemption")) && (isToday==false)) {
            return (row == 4 && col == 2) || (row == 4 && col == 3)|| (row == 4 && col == 4);
        }
        return false;
    }

}
