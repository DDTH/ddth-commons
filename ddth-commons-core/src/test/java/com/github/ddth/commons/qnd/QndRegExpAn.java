package com.github.ddth.commons.qnd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QndRegExpAn {
    static String repeat(String input, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(input);
        }
        return sb.toString();
    }

    static void doTest(int n) {
        String needle = repeat("a?", n) + repeat("a", n);
        String input = repeat("a", n);
        Matcher m = Pattern.compile(needle).matcher(input);
        long t = System.currentTimeMillis();
        if (!m.find()) {
            System.out.println("Hmm, something wrong here!");
        } else {
            System.out.println(m.group());
        }
        long d = System.currentTimeMillis() - t;
        System.out.println("Match a?^" + n + "a^" + n + " against a^" + n + " in " + d + "ms.");
    }

    public static void main(String[] args) {
        for (int i = 1; i < 30; i++) {
            doTest(i);
        }
    }
}
