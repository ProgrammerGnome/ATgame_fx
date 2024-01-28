package org.example.atgame.GlueComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomTextToRegexgen {

    public List<String> main(/*String[] args*/) {
        //List<String> randomStrings = generateRandomStrings(250, 10);

        var randomStrings = new ArrayList<String>();

        randomStrings.add("a");
        randomStrings.add("ab");
        randomStrings.add("abb");
        randomStrings.add("cca");
        randomStrings.add("add");
        randomStrings.add("eea");
        randomStrings.add("aecd");
        randomStrings.add("abee");
        randomStrings.add("baa");
        randomStrings.add("eeb");
        randomStrings.add("adbe");
        randomStrings.add("abba");
        randomStrings.add("ae");
        randomStrings.add("dada");
        randomStrings.add("ebbe");
        randomStrings.add("cacb");
        randomStrings.add("bab");
        randomStrings.add("ea");
        randomStrings.add("bad");
        randomStrings.add("dda");

        Collections.shuffle(randomStrings, new Random());
        List<String> selectedItems = randomStrings.subList(0, 2);

        var randomStrings2 = new ArrayList<>(randomStrings);
        Collections.shuffle(randomStrings2, new Random());
        List<String> selectedItems2 = randomStrings2.subList(0, 2);

        var randomStrings3 = new ArrayList<>(randomStrings2);
        Collections.shuffle(randomStrings3, new Random());
        List<String> selectedItems3 = randomStrings3.subList(0, 2);

        var x = new ArrayList<String>();
        x.addAll(selectedItems);
        x.addAll(selectedItems2);
        x.addAll(selectedItems3);

        // Kiíratás a konzolra
        for (String str : randomStrings) {
            System.out.println(str);
        }
        //return randomStrings;
        return x;
    }

//    // Metódus a random stringek generálására
//    private static List<String> generateRandomStrings(int count, int length) {
//        List<String> randomStrings = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            String randomString = generateRandomString(length);
//            randomStrings.add(randomString);
//        }
//
//        return randomStrings;
//    }
//
//    // Metódus egy random string generálására adott hosszúságú karakterlánccal
//    private static String generateRandomString(int length) {
//        // Megengedett karakterek
//        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//
//        // Random generátor inicializálása
//        Random random = new Random();
//
//        // StringBuilder az eredmény létrehozásához
//        StringBuilder sb = new StringBuilder(length);
//
//        // Megadott hosszúságú random karakterlánc létrehozása
//        for (int i = 0; i < length; i++) {
//            int randomIndex = random.nextInt(allowedCharacters.length());
//            char randomChar = allowedCharacters.charAt(randomIndex);
//            sb.append(randomChar);
//        }
//
//        return sb.toString();
//    }
}