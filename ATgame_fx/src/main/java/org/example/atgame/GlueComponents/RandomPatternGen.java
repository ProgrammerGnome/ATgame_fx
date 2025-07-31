package org.example.atgame.GlueComponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class RandomPatternGen {
    public String main(/*String[] args*/) {
        try {
            // A futtatni kívánt parancs
            //String command = "node_modules/.bin/regexgen aab aaab aaaabc 4e453 alta aaabccb";

            RandomTextToRegexgen randomTextToRegexgen = new RandomTextToRegexgen();
            //randomTextToRegexgen.main();

            List<String> randomWordList = randomTextToRegexgen.main();
            String baseString = "node_modules/.bin/regexgen ";

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(baseString);

            for (var i : randomWordList){
                stringBuilder.append(i).append(" ");
            }
            String result = stringBuilder.toString();

            String command = result;
            //System.out.println(command);



            // TODO: /ppYIFJJTnK|tWhZjyr2tj|ikXhsJYeBL|7KzdJwUtZA|8ntxv18dPz|rMxJzhMstt|6(?:UhE5bnegH|YhEhhbiHe)|3KBEs9X8GU|48LoGTVytG|7wKfl1Hfru|9(?:Wctarle4u|cvQ2wT0SW)|E9knK14HQT|XT0jYieVLd|vA5sFgyYGI|1R2p4ahN4c/

            // TODO: : -> * és $ -> +

            // A parancs futtatása
            Process process = Runtime.getRuntime().exec(command);

            // A parancs kimenetének olvasása
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String sophisticaedLine = "";
            String randomChar = getSaltString();
            while ((line = reader.readLine()) != null) {
                sophisticaedLine = line.
                        replace(":","*").
                        replace("$","+").
                        replace("/","").
                        replace("?",randomChar).
                        replace("[","+").
                        replace("]","*");
                System.out.println("Ez az igazi regex: "+sophisticaedLine);
                // TODO: ...
            }

            // A parancs lefutásának várása
            int exitCode = process.waitFor();
            System.out.println("A parancs lefutott, visszatérési kód: " + exitCode);

            return sophisticaedLine;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getSaltString() {
        //String SALTCHARS = "abcdefghijklmnopqrsztyxz";
        String SALTCHARS = "abc";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.isEmpty()) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

}


