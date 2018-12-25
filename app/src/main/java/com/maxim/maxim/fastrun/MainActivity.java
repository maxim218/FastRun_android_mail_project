package com.maxim.maxim.fastrun;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    long globalRowId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        new DatabaseAsyncTask().execute();
    }

    public void closeApp(View view) {
        finish();
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    private void makeMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
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

    public long workWithDatabase() {
        ManagerSQL managerSQL = new ManagerSQL(this);
        SQLiteDatabase db = managerSQL.getWritableDatabase();

        Calendar cal = Calendar.getInstance();
        Date date = cal. getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = dateFormat. format(date);

        ContentValues cv = new ContentValues();
        cv.put("date", formattedDate);
        long rowID = db.insert("visits", null, cv);
        return Math.abs(rowID);
    }

    class DatabaseAsyncTask extends AsyncTask<Void, Void, Void>  {
        @Override
        protected Void doInBackground(Void... voids) {
            globalRowId = workWithDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            makeMessage("Visits number: " + globalRowId);
        }
    }
}

class ManagerSQL extends SQLiteOpenHelper {
    public ManagerSQL(Context context) {
        super(context, "base", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String content = " create table visits (id integer primary key autoincrement, date text); ";
        db.execSQL(content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do nothing
    }
}