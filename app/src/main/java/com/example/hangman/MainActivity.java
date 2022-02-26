
package com.example.hangman;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvWord;
    // new added
    private TextView tvHint;
    private Button btnHint;

    private ImageView imgHangman;
    private Word word;
    private int turn;
    private ArrayList<Integer> pressedID = new ArrayList<>(26);

    public static final int MAX_WRONG = 8;

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
        tvHint = findViewById(R.id.tv_hint);
        btnHint = findViewById(R.id.bt_hint);

        if (tvHint != null) {
            tvHint = (TextView) findViewById(R.id.tv_hint);
            tvHint.setVisibility(View.INVISIBLE);
            btnHint.setOnClickListener(v -> {
                tvHint.setVisibility(View.VISIBLE);
                turn++;
            });
            showImage();
        }

        if (savedInstanceState == null)
            newGame();
    }

    public void onButtonClick(View view) {
        char letter = ((String) view.getTag()).charAt(0);
        // do the word check
        if (!word.hasChar(letter)) {
            turn++;
            // hang man image
            showImage();
        }

        // disable the button
        view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        view.setEnabled(false);

        // show the text view
        tvWord.setText(word.toString());

        // save pressed button
        pressedID.add(view.getId());

        if (isGameEnd()) {
            String text = null;
            if (isWin())
                text = "You win";
            else
                text = "You lose";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(text)
                    .setTitle("Game End");

            AlertDialog dialog = builder.create();
            dialog.show();
            newGame();
        }
    }

    private void showImage() {
        switch (turn) {
            case 1:
                imgHangman.setImageResource(R.drawable.hangman1);
                break;
            case 2:
                imgHangman.setImageResource(R.drawable.hangman2);
                break;
            case 3:
                imgHangman.setImageResource(R.drawable.hangman3);
                break;
            case 4:
                imgHangman.setImageResource(R.drawable.hangman4);
                break;
            case 5:
                imgHangman.setImageResource(R.drawable.hangman5);
                break;
            case 6:
                imgHangman.setImageResource(R.drawable.hangman6);
                break;
            case 7:
                imgHangman.setImageResource(R.drawable.hangman7);
                break;
        }
    }

    private void newGame() {
        for (int id : pressedID) {
            View view = findViewById(id);
            view.getBackground().clearColorFilter();
            view.setEnabled(true);
        }
        word.generateNewWord();
        tvWord.setText(word.toString());
        imgHangman.setImageDrawable(null);
        if (tvHint != null)
            tvHint.setVisibility(View.INVISIBLE);
        turn = 0;
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

        // save all button
        outState.putIntegerArrayList("pressed", pressedID);
        outState.putCharArray("word", word.getWord());
        outState.putString("hint", word.getHint());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<Integer> pressed = savedInstanceState.getIntegerArrayList("pressed");
        word = new Word(savedInstanceState.getCharArray("word"),
                savedInstanceState.getString("hint"));
        for (int id : pressed)
            // resume button state
            onButtonClick(findViewById(id));
        // resume turn and image
        showImage();
    }
}