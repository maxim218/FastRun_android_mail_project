package com.maxim.maxim.fastrun;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void closeApp(View view) {
        finish();
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    private void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void loadRecords(View view) {
        DataWorker dataWorker = new DataWorker();
        String content = dataWorker.loadData(getApplicationContext());
        if(content.equals("")) {
            String message = "Records not found";
            makeMessage(message);
        } else {
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
        }
    }

    public void openAboutActivity(View view) {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }
}
