package com.maxim.maxim.fastrun;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String localeValue =  Locale.getDefault().toString();
        String content = "";
        String header = "";

        if(localeValue.equals("en_US")) {
            content = getString(R.string.english);
            header = getString(R.string.englishHeader);
        } else {
            content = getString(R.string.russian);
            header = getString(R.string.russianHeader);
        }

        TextView headerLabel = (TextView) findViewById(R.id.header_text_view);
        TextView contentLabel = (TextView) findViewById(R.id.content_text_view);

        headerLabel.setText(header);
        contentLabel.setText(content);
    }
}
