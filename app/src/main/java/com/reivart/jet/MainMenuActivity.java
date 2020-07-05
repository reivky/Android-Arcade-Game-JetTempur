package com.reivart.jet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mPlay, mHighScore,mAbout, mExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPlay = findViewById(R.id.play);
        mHighScore = findViewById(R.id.high_score);
        mAbout = findViewById(R.id.about);
        mExit = findViewById(R.id.exit);

        mPlay.setOnClickListener(this);
        mHighScore.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.high_score:
                startActivity(new Intent(this, HighScoreActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
