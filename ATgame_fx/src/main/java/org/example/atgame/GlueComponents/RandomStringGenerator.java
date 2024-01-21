package org.example.atgame.GlueComponents;

import com.github.curiousoddman.rgxgen.RgxGen;

public class RandomStringGenerator {

    // Enter your pattern here.
    public static String PATTERN = "(aba)*a|b";

    public static void main(String[] args) {

        RgxGen rgxGen = new RgxGen(PATTERN);
        System.out.println("10 matching: ");
        for (int i = 0; i < 50; ++i) {
            System.out.println("\t" + rgxGen.generate());
        }

    }

}
