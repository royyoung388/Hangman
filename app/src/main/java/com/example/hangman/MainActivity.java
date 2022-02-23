
package com.example.hangman;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord;
    private ImageView imgHangman;
    private Word word;
    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        word = new Word();
        tvWord = findViewById(R.id.tv_word);
        imgHangman = findViewById(R.id.img_hangman);

        newGame();
    }

    public void onButtonClick(View view) {
        char letter = (char) view.getTag();
        // do the word check
        if (word.check(letter)) {
            turn++;
            // hang man image
            imgHangman.setImageResource(R.drawable.hangman);
        }

        // disable the button
        view.setBackgroundColor(Color.GRAY);
        view.setEnabled(false);

        // show the text view
        tvWord.setText(word.getWord());
    }

    public void newGame() {
        word.generateNewWord();
        tvWord.setText(word.getWord());
        turn = 0;
    }
}