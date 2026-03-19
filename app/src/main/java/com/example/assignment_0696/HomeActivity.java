package com.example.assignment_0696;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    String[][] movies = {
            {"The Dark Knight", "Action | 152 min"},
            {"Inception", "Sci-Fi | 148 min"},
            {"Interstellar", "Sci-Fi | 169 min"},
            {"The Shawshank Redemption", "Drama | 142 min"}
    };
    private boolean isToday=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Button btntoday=findViewById(R.id.btnToday);
        Button btntomorrow=findViewById(R.id.btnTomorrow);
        Button btnbookDK=findViewById(R.id.btnbookDK);
        Button btnbookIP=findViewById(R.id.btnbookIP);
        Button btnbookIS=findViewById(R.id.btnbookIS);
        Button btnbookSSR=findViewById(R.id.btnbookSSR);
        Button btntrailerdk=findViewById(R.id.trailerDK);
        Button btntrailerip=findViewById(R.id.trailerIP);
        Button btntraileris=findViewById(R.id.trailerIS);
        Button btntrailerssr=findViewById(R.id.trailerSSR);
        init();

        btntoday.setOnClickListener(v -> selectButton(btntoday,btntomorrow));
        btntomorrow.setOnClickListener(v -> selectButton(btntomorrow,btntoday));
        btntrailerdk.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=EXeTwQWrcwY"));
        btntrailerip.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=YoHD9XEInc0"));
        btntraileris.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=zSWdZVtXT7E"));
        btntrailerssr.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=PLl99DlL6b4"));
        btnbookDK.setOnClickListener(v -> openseatselection("The Dark Knight"));
        btnbookIP.setOnClickListener(v -> openseatselection("Inception"));
        btnbookIS.setOnClickListener(v -> openseatselection("Interstellar"));
        btnbookSSR.setOnClickListener(v -> openseatselection("The Shawshank Redemption"));
    }
    public void selectButton(Button selected,Button other)
    {
        isToday = (selected.getId() == R.id.btnToday);
        selected.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        other.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
    }
    public void init()
    {
        ImageView name =findViewById(R.id.ivlogo);
        TextView SelectDate =findViewById(R.id.tvSelectDate);
        TextView tvNowShowing=findViewById(R.id.tvNowShowing);
        ImageView dk=findViewById(R.id.darkknight);
        ImageView ic=findViewById(R.id.inception);
        ImageView is=findViewById(R.id.interstellar);
        ImageView ssr=findViewById(R.id.ssr);
        TextView tvdarkknight=findViewById(R.id.tvdarkknight);
        TextView tvinception=findViewById(R.id.tvinception);
        TextView tvinterstellar=findViewById(R.id.tvinterstellar);
        TextView tvssr=findViewById(R.id.tvSSR);
        TextView tvdktime=findViewById(R.id.tvdktime);
        TextView tvinterstellartime=findViewById(R.id.tvinterstellartime);
        TextView tvinceptiontime=findViewById(R.id.tvinceptiontime);
        TextView tvssrtime=findViewById(R.id.tvSSRtime);
    }
    private void openTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    private void openseatselection(String movieName) {
        Intent intent = new Intent(this, SeatSelectionActivity.class);
        if(movieName == "The Dark Knight"){
            intent.putExtra("hallno","1st");
            intent.putExtra("time","11:30");
        }else if(movieName == "Inception"){
            intent.putExtra("hallno","2nd");
            intent.putExtra("time","1:30");
        }else if(movieName == "Interstellar"){
            intent.putExtra("hallno","3rd");
            intent.putExtra("time","3:30");
        }else if(movieName == "The Shawshank Redemption"){
            intent.putExtra("hallno","4th");
            intent.putExtra("time","5:30");
        }
        intent.putExtra("movie_name", movieName);
        intent.putExtra("is_today", isToday);
        startActivity(intent);
    }


}
