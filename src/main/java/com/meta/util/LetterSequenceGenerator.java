package com.meta.util;

public class LetterSequenceGenerator {

    private int callCount = 0;

    private static final int LETTERS_IN_ALPHABET = 26;
    private static final char FIRST_LETTER = 'a';

    public synchronized String getNextSequence() {
        callCount++;

        if (callCount <= LETTERS_IN_ALPHABET) {
            // 1-26次调用：返回单个字母 a-z
            return String.valueOf((char)(FIRST_LETTER + callCount - 1));
        } else {
            // 超过26次调用：返回 aa, bb, cc,...zz
            int repeatCount = (callCount - 1) / LETTERS_IN_ALPHABET + 1;
            char currentChar = (char)(FIRST_LETTER + (callCount - 1) % LETTERS_IN_ALPHABET);
            return String.valueOf(currentChar).repeat(repeatCount);
        }
    }

    // 重置计数器
    public synchronized void reset() {
        callCount = 0;
    }
}
