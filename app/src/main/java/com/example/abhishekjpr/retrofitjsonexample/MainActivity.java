package com.example.abhishekjpr.retrofitjsonexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.abhishekjpr.retrofitjsonexample.data.model.Weather;
import com.example.abhishekjpr.retrofitjsonexample.data.remote.WeatherAPI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.cityTextview)
    TextView cityTextview;
    @BindView(R.id.dateAndTimeTextview)
    TextView dateAndTimeTextview;
    @BindView(R.id.tempTextview)
    TextView tempTextview;
    @BindView(R.id.statusTextview)
    TextView statusTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.refreshButton)
    public void updateData(){
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                String city = response.body().getQuery().getResults().getChannel().getLocation().getCity()+", "+response.body().getQuery().getResults().getChannel().getLocation().getCountry();
                cityTextview.setText(city);
                tempTextview.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getTemp());
                dateAndTimeTextview.setText(response.body().getQuery().getResults().getChannel().getLastBuildDate());
                statusTextview.setText(response.body().getQuery().getResults().getChannel().getItem().getCondition().getText());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }
}
