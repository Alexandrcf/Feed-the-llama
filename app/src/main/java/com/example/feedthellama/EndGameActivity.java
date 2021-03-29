package com.example.feedthellama;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private Button buttonNewGame;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        buttonNewGame = (Button) findViewById(R.id.button_new_game);
        tvCount = (TextView) findViewById(R.id.tv_count);
        String count = getIntent().getExtras().get("value").toString();
        tvCount.setText("Съедено травы: " + count);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivityGame.class);
                startActivity(intent);
                finish();
            }
        });
    }
}