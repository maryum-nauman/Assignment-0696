package com.example.assignment_0696;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SnacksandDrinksFragment extends Fragment {

    private int pc, nachos, sd, cm;
    private ArrayList<String> selectedSeatsList;

    public SnacksandDrinksFragment() {
        super(R.layout.fragment_snacks);
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

        ListView listView = view.findViewById(R.id.snackList);

        ArrayList<Snack> snackList = new ArrayList<>();

        snackList.add(new Snack(R.drawable.popcorn, "Popcorn", "$8.99"));
        snackList.add(new Snack(R.drawable.nachos, "Nachos", "$7.99"));
        snackList.add(new Snack(R.drawable.softdrink, "Soft Drink", "$5.99"));
        snackList.add(new Snack(R.drawable.candymix, "Candy Mix", "$6.99"));
        SnackAdapter adapter = new SnackAdapter(getActivity(), snackList);
        listView.setAdapter(adapter);

        Button btnConfirmOrder = view.findViewById(R.id.btnConfirmOrder);
        btnConfirmOrder.setOnClickListener(v -> {
            int pc = snackList.get(0).getQuantity();
            int nachos = snackList.get(1).getQuantity();
            int sd = snackList.get(2).getQuantity();
            int cm = snackList.get(3).getQuantity();

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("selected_seats", selectedSeatsList);
            bundle.putString("movie_name", moviename);
            bundle.putInt("seatCount", seatCount);
            bundle.putInt("popcorn", pc);
            bundle.putInt("nachos", nachos);
            bundle.putInt("softdrink", sd);
            bundle.putInt("candymix", cm);
            bundle.putString("time", time);
            bundle.putString("date", date);
            bundle.putString("hallno", hall);

            TicketSummaryFragment fragment = new TicketSummaryFragment();
            fragment.setArguments(bundle);

            ((MainActivity) requireActivity()).loadFragment(fragment);
        });
    }
}