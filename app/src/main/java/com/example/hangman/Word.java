package com.example.hangman;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Word {
    private char[] word;
    private int[] mask;

    public Word() {
        generateNewWord();
    }

    /**
     * input the button value, and check the integrity
     * @param c  button value
     */
    public boolean check(char c) {
        boolean flag = false;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == c) {
                mask[i] = 1;
                flag = true;
            }
        }
        return flag;
    }

    public void generateNewWord() {
        word = new char[] {};
        mask = new int[word.length];
    }

    public String getWord() {
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
}
