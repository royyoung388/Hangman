
package com.example.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord;
    private ImageView imgHangman;
    private Word word;
    private int turn;
    private ArrayList<Integer> pressedID = new ArrayList<>(26);

    public static final int MAX_WRONG = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide action bar
        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        word = new Word();
        tvWord = findViewById(R.id.tv_word);
        imgHangman = findViewById(R.id.img_hangman);

        if (savedInstanceState == null)
            newGame();
        else
            resumeGame();
    }

    public void onButtonClick(View view) {
        char letter = ((String) view.getTag()).charAt(0);
        // do the word check
        if (!word.hasChar(letter)) {
            turn++;
            // todo 1. use different image to represent the process of hangman
            // todo 2. show image at correct turn
            // hang man image
            switch (turn) {
                case 1:
                    imgHangman.setImageResource(R.drawable.hangman);
                    break;
            }
        }

        // disable the button
        view.setBackgroundColor(Color.GRAY);
        view.setEnabled(false);

        // show the text view
        tvWord.setText(word.toString());

        // save pressed button
        pressedID.add(view.getId());

        if (isGameEnd()) {
            if (isWin())
                Toast.makeText(this, "Game End: You win", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Game End: You lose", Toast.LENGTH_SHORT).show();
            newGame();
        }
    }

    private void newGame() {
        word.generateNewWord();
        tvWord.setText(word.toString());
        turn = 0;
    }

    private void resumeGame() {
        for (int id : pressedID)
            // resume button state
            onButtonClick(findViewById(id));
        // resume turn and image
        imgHangman.setImageResource(R.drawable.hangman);
    }

    private boolean isGameEnd() {
        if (word.isAllMatch())
            return true;
        return turn >= MAX_WRONG;
    }

    private boolean isWin() {
        return word.isAllMatch();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("turn", turn);
        // save all button
        outState.putIntegerArrayList("pressed", pressedID);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        turn = savedInstanceState.getInt("turn");
        pressedID = savedInstanceState.getIntegerArrayList("pressed");
    }
}