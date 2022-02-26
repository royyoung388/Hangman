package com.example.hangman;

import androidx.annotation.NonNull;

import java.util.Random;

/**
 *
 */
public class Word {

    // todo hard code word
    private static final String[] WORD = {"HANGMAN", "WINDY", "WINTER", "NINE", "PIZZA", "BOSTON"};
    private static final String[] HINT = {"GAME", "WEATHER", "SEASON", "NUMBER", "FOOD", "CITY"};


    private char[] word;
    private int[] mask;
    private String hint;

    public Word() {
        generateNewWord();
    }

    public Word(char[] word, String hint) {
        this.word = word;
        mask = new int[word.length];
        this.hint = hint;
    }

    /**
     * input the button value, and check the integrity
     *
     * @param c button value
     */
    public boolean hasChar(char c) {
        boolean flag = false;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == c) {
                mask[i] = 1;
                flag = true;
            }
        }
        return flag;
    }

    /**
     * generate word to use.
     * Can be hard code. Like, use a list to store all word, then random choose one.
     */
    public void generateNewWord() {
        int rnd = new Random().nextInt(WORD.length);
        String choose = WORD[rnd];

        word = new char[choose.length()];
        mask = new int[word.length];
        hint = HINT[rnd];

        for (int i = 0; i < choose.length(); i++)
            word[i] = choose.charAt(i);
    }

    public char[] getWord() {
        return word;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length; i++) {
            if (mask[i] == 1) {
                result.append(word[i]);
            } else {
                result.append("__");
            }
            result.append(" ");
        }
        return result.toString();
    }

    public boolean isAllMatch() {
        for (int i : mask)
            if (i == 0)
                return false;
        return true;
    }
}
