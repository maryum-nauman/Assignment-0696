package com.example.assignment_0696;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TicketSummaryFragment extends AppCompatActivity {

    private java.util.ArrayList<String> selectedSeatsList = new java.util.ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ImageButton btnbackticket = findViewById(R.id.btnbackticket);
        ImageButton btnmenuticket = findViewById(R.id.btnmenuticket);
        Button btnSend=findViewById(R.id.btnSend);
        TextView tvDate=findViewById(R.id.tvDate);
        TextView tvTime=findViewById(R.id.tvTime);
        TextView tvHall=findViewById(R.id.tvHall);
        TextView tvmoviename=findViewById(R.id.tvticketname);
        ImageView ivmovie=findViewById(R.id.ticketimage);
        TextView tvSummaryList = findViewById(R.id.tvSummaryList);
        TextView tvTotalValue = findViewById(R.id.tvTotalValue);


        String moviename=getIntent().getStringExtra("movie_name");
        int seatCount=getIntent().getIntExtra("seatCount",1);
        selectedSeatsList=getIntent().getStringArrayListExtra("selected_seats");
        String date=getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String hall = getIntent().getStringExtra("hallno");
        tvDate.setText(date);
        tvHall.setText(hall);
        tvTime.setText(time);
        tvmoviename.setText(moviename);
        int pc=getIntent().getIntExtra("popcorn",0);
        int nachos=getIntent().getIntExtra("nachos",0);
        int sd=getIntent().getIntExtra("softdrink",0);
        int cm=getIntent().getIntExtra("candymix",0);

        if(moviename.equals("The Dark Knight")){
            ivmovie.setImageResource(R.drawable.thedarkknight);
        }else if(moviename.equals("Inception")){
            ivmovie.setImageResource(R.drawable.inception);
        }else if(moviename.equals("Interstellar")){
            ivmovie.setImageResource(R.drawable.interstellar);
        }else if(moviename.equals("The Shawshank Redemption")){
            ivmovie.setImageResource(R.drawable.theshawshankredemption);
        }

        double total = 0;
        double ticketPrice = 16.0;
        StringBuilder summary = new StringBuilder("Tickets\n");
        if (selectedSeatsList != null) {
            for (String seat : selectedSeatsList) {
                summary.append(seat).append("                                              16 USD\n");
                total += ticketPrice;
            }
        }
        summary.append("\nSnacks and Drinks\n");
        if (pc > 0) {
            summary.append("X").append(pc).append(" Popcorn                                                 ").append(pc * 8.99).append(" USD\n");
            total += (pc * 8.99);
        }
        if (nachos > 0) {
            summary.append("X").append(nachos).append(" Nachos                                                  ").append(nachos * 7.99).append(" USD\n");
            total += (nachos * 7.99);
        }
        if (sd > 0) {
            summary.append("X").append(sd).append(" Soft Drinks                                            ").append(sd * 5.99).append(" USD\n");
            total += (sd * 5.99);
        }
        if (cm > 0) {
            summary.append("X").append(cm).append(" Candy Mix                                              ").append(cm * 6.99).append(" USD\n");
            total += (cm * 6.99);
        }

        tvSummaryList.setText(summary.toString());
        tvTotalValue.setText(String.format(Locale.getDefault(), "%.2f USD", total));

        btnbackticket.setOnClickListener(v -> {
            finish();
        });

        double finalTotal = total;
        btnSend.setOnClickListener(v -> {
            String ticket="🎬 MOVIE TICKET CONFIRMED\n\n" + "Movie: " + moviename
                    + "\n" + "Seats: " + (selectedSeatsList != null ? selectedSeatsList.toString() : "0") + "\n" +
                    "Date: " + date + "\n" + "Time: " + time + "\n" +
                    "Total Amount: " + String.format(Locale.getDefault(), "%.2f USD", finalTotal);

            Intent intent= new Intent (Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Movie Name: " + moviename);
            intent.putExtra(Intent.EXTRA_TEXT, (CharSequence) summary);
            startActivity(Intent.createChooser(intent,"Share Ticket via:"));

            try {
                startActivity(intent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "WhatsApp not installed!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
