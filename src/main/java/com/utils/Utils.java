package com.utils;

import java.util.Random;

/**
 * Created by Zveriki on 27.05.2017.
 */
public class Utils {

    private static final char[] symbols;

    private static final Random random = new Random();

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    public static String randomString(int minLength, int maxLenght) {
        if (maxLenght <= minLength) throw new IllegalArgumentException();
        int lenght = random.nextInt(maxLenght - minLength) + minLength;
        char[] buf = new char[lenght];
        for (int idx = 0; idx < lenght; idx++)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static int randomInteger(int min, int max) {
        if (max < min) throw new IllegalArgumentException();
        return random.nextInt(max - min) + min;
    }
}
