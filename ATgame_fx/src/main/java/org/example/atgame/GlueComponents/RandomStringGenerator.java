package org.example.atgame.GlueComponents;

import com.github.curiousoddman.rgxgen.RgxGen;

import java.util.ArrayList;

public class RandomStringGenerator {

    // Enter your pattern here.
    //public static String PATTERN = "a(r*a(r*a(r*bccb|abc)|ab|b)|lta)|4e453";
    RandomPatternGen randomPatternGen = new RandomPatternGen();
    public String PATTERN = randomPatternGen.main();
    //aa(a*(a+bc|b)|b);
    //(c+b)(c+(abca)(a+b))*b((cc)*+b+bc+a)(bc*);
    //(aba)*a|b

        public ArrayList<String> main(/*String[] args*/) {

        RgxGen rgxGen = new RgxGen(PATTERN);
        var wordList = new ArrayList<String>();

        System.out.println("50 matching: ");
        for (int i = 0; i < 50; ++i) {
            System.out.println("\t" + rgxGen.generate());
            wordList.add(rgxGen.generate());
        }

        return wordList;
    }

}
