package com.maxim.maxim.fastrun;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {
    String allContent = "";
    ManagerSQL managerSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        managerSQL = new ManagerSQL(this);
        new DatabaseAsyncTaskSecond().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class DatabaseAsyncTaskSecond extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            allContent = "";
            SQLiteDatabase db = managerSQL.getReadableDatabase();
            Cursor c = db.query("visits", null, null, null, null, null, null);
            if(c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String date = c.getString(c.getColumnIndex("date"));
                    allContent = allContent + "Id: " + id + "   Date: " + date + "\n\n";
                } while (c.moveToNext());
            } else {
                Log.d("Message", "empty");
            }
            c.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView answerField = (TextView) findViewById(R.id.content_text_view);
            answerField.setText(allContent);
        }
    }
}

