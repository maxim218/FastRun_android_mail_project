package com.maxim.maxim.fastrun;

import android.content.Context;
import android.content.SharedPreferences;

public class DataWorker {
    private SharedPreferences sPref = null;
    private String name = "FAST_RUN_GAME";
    private String key = "DATA_KEY";

    public String loadData(Context context) {
        sPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String defaultValue = "";
        return sPref.getString(key, defaultValue);
    }

    public void saveData(Context context, String data) {
        sPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        String oldContent = loadData(context);
        SharedPreferences.Editor editor = sPref.edit();
        String newContent = oldContent + data + "_";
        editor.putString(key, newContent);
        editor.apply();
    }
}
