package com.example.assignment_0696;

import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class SnackAdapter extends ArrayAdapter<Snack> {

    public SnackAdapter(Context context, ArrayList<Snack> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_snack, parent, false);
        }

        Snack snack = getItem(position);

        ImageView img = convertView.findViewById(R.id.ivsnackimg);
        TextView name = convertView.findViewById(R.id.tvsnackname);
        TextView price = convertView.findViewById(R.id.tvsnackprice);
        TextView qty = convertView.findViewById(R.id.tvquantity);
        Button plus = convertView.findViewById(R.id.btnplus);
        Button minus = convertView.findViewById(R.id.btnminus);

        img.setImageResource(snack.getImage());
        name.setText(snack.getName());
        price.setText(snack.getPrice());
        qty.setText(String.valueOf(snack.getQuantity()));

        plus.setOnClickListener(v -> {
            snack.setQuantity(snack.getQuantity() + 1);
            qty.setText(String.valueOf(snack.getQuantity()));
        });

        minus.setOnClickListener(v -> {
            if (snack.getQuantity() > 0) {
                snack.setQuantity(snack.getQuantity() - 1);
                qty.setText(String.valueOf(snack.getQuantity()));
            }
        });

        return convertView;
    }
}