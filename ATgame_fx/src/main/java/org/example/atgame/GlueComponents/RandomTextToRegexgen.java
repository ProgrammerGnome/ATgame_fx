package org.example.atgame.GlueComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTextToRegexgen {

    public List<String> main(/*String[] args*/) {
        List<String> randomStrings = generateRandomStrings(250, 10);

        // Kiíratás a konzolra
        for (String str : randomStrings) {
            System.out.println(str);
        }
        return randomStrings;
    }

    // Metódus a random stringek generálására
    private static List<String> generateRandomStrings(int count, int length) {
        List<String> randomStrings = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String randomString = generateRandomString(length);
            randomStrings.add(randomString);
        }

        return randomStrings;
    }

    // Metódus egy random string generálására adott hosszúságú karakterlánccal
    private static String generateRandomString(int length) {
        // Megengedett karakterek
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Random generátor inicializálása
        Random random = new Random();

        // StringBuilder az eredmény létrehozásához
        StringBuilder sb = new StringBuilder(length);

        // Megadott hosszúságú random karakterlánc létrehozása
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}