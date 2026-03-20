package com.example.assignment_0696;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class SnacksandDrinksFragment extends AppCompatActivity{
    private int pc,nachos,sd,cm;
    private java.util.ArrayList<String> selectedSeatsList = new java.util.ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        String moviename=getIntent().getStringExtra("movie_name");
        int seatCount=getIntent().getIntExtra("seatCount",1);
        selectedSeatsList=getIntent().getStringArrayListExtra("selected_seats");
        String date=getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String hall = getIntent().getStringExtra("hallno");

        Button btnpluspc=findViewById(R.id.btnpluspc);
        Button btnminuspc=findViewById(R.id.btnminuspc);
        TextView tvcountpc=findViewById(R.id.tvcountpc);
        Button btnplusn=findViewById(R.id.btnplusn);
        Button btnminusn=findViewById(R.id.btnminusn);
        TextView tvcountn=findViewById(R.id.tvcountn);
        Button btnplussd=findViewById(R.id.btnplussd);
        Button btnminussd=findViewById(R.id.btnminussd);
        TextView tvcountsd=findViewById(R.id.tvcountsd);
        Button btnpluscm=findViewById(R.id.btnpluscm);
        Button btnminuscm=findViewById(R.id.btnminuscm);
        TextView tvcountcm=findViewById(R.id.tvcountcm);

        btnpluspc.setOnClickListener(v -> {
            pc=pc+1;
            tvcountpc.setText(String.valueOf(pc));
        });
        btnminuspc.setOnClickListener(v -> {
            if(pc>0){
                pc=pc-1;
                tvcountpc.setText(String.valueOf(pc));
            }
        });

        btnplusn.setOnClickListener(v -> {
            nachos=nachos+1;
            tvcountn.setText(String.valueOf(nachos));
        });
        btnminusn.setOnClickListener(v -> {
            if(nachos>0){
                nachos=nachos-1;
                tvcountn.setText(String.valueOf(nachos));
            }
        });

        btnplussd.setOnClickListener(v -> {
            sd=sd+1;
            tvcountsd.setText(String.valueOf(sd));
        });
        btnminussd.setOnClickListener(v -> {
            if(sd>0){
                sd=sd-1;
                tvcountsd.setText(String.valueOf(sd));
            }
        });

        btnpluscm.setOnClickListener(v -> {
            cm=cm+1;
            tvcountcm.setText(String.valueOf(cm));
        });
        btnminuscm.setOnClickListener(v -> {
            if(cm>0){
                cm=cm-1;
                tvcountcm.setText(String.valueOf(cm));
            }
        });

        Button btnConfirmOrder=findViewById(R.id.btnConfirmOrder);
        btnConfirmOrder.setOnClickListener(v -> {
            Intent intent=new Intent(SnacksandDrinksFragment.this, TicketSummaryFragment.class);
            intent.putStringArrayListExtra("selected_seats", selectedSeatsList);
            intent.putExtra("movie_name", moviename);
            intent.putExtra("seatCount",seatCount);
            intent.putExtra("popcorn",pc);
            intent.putExtra("nachos",nachos);
            intent.putExtra("softdrink",sd);
            intent.putExtra("candymix",cm);
            intent.putExtra("time",time);
            intent.putExtra("date",date);
            intent.putExtra("hallno",hall);
            startActivity(intent);
        });
    }
}
