package com.xyh.sleeper;

import java.util.Random;

/**
 * Created by xyh on 2017/7/17.
 */

public class Test {
    public static void main(String[] args) {
        String key = "QSt3UHp0bmxHeVVYdnVUekpIZFJmL1Q3ektiUHlocDFja0xyT1QzUCtzVVBSMDhhWk84VW8wYU1TSFEzRFE5S0N5dHFGV2tPd2Fvbw==";
        System.out.println("key.length():"+key.length());
        byte[] keyBytes = key.getBytes();
        byte[] randomKey = getRandomStr(key.length()).getBytes();

        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) (keyBytes[i] ^ (int) (randomKey[i]));
        }
        System.out.println("keyBytes.length:"+keyBytes.length);
        System.out.println(new String(keyBytes));

        for (int i = 0; i < keyBytes.length; i++) {
            keyBytes[i] = (byte) (keyBytes[i] ^ (int) (randomKey[i]));
        }
        System.out.println(new String(keyBytes));

    }

    public static String getRandomStr(int len) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
