package com.maxim.maxim.fastrun;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DataWorker dataWorker = new DataWorker();
        String content = dataWorker.loadData(getApplicationContext());
        String [] arr = content.split("_");

        ScoreDataAdapter adapter = new ScoreDataAdapter(arr, this);
        recyclerView.setAdapter(adapter);
    }
}

class ScoreDataAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private String [] array;

    class ElementOfList extends RecyclerView.ViewHolder {
        public TextView gameNumberField;
        public TextView scoreNumberField;
        public ElementOfList(View itemView) {
            super(itemView);
            gameNumberField = (TextView) itemView.findViewById(R.id.game_number_field);
            scoreNumberField = (TextView) itemView.findViewById(R.id.score_number_field);
        }
    }

    ScoreDataAdapter(String[] arrayParam, Main3Activity main3Activity) {
        array = arrayParam;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_card, parent, false);
        return new ElementOfList(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ElementOfList holderElement = (ElementOfList) holder;
        String gameString = "Game: " + (position + 1);
        String scoreString = "Score: " + array[position];
        holderElement.gameNumberField.setText(gameString);
        holderElement.scoreNumberField.setText(scoreString);
    }


    @Override
    public int getItemCount() {
        return array.length;
    }
}
