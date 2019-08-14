package com.example.alexr.ideamanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.alexr.ideamanager.services.MessageService;
import com.example.alexr.ideamanager.services.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        MessageService taskService = ServiceBuilder.buildService(MessageService.class);
        Call<String> call = taskService.getMessages("http://192.168.0.7:9000/messages");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> request, Response<String> response) {
                ((TextView) findViewById(R.id.message)).setText(response.body());
            }

            @Override
            public void onFailure(Call<String> request, Throwable t) {
                ((TextView) findViewById(R.id.message)).setText("Request Failed");
            }
        });
    }

    public void GetStarted(View view){
        Intent intent = new Intent(this, IdeaListActivity.class);
        startActivity(intent);
    }
}
