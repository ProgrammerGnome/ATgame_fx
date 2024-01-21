package org.example.atgame.RegexToDFA;

//import org.example.atgame.RegexToDFA.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserNFAtoDFA {
    public static String regexp;
    public static AFN afn;
    public static DFA dfa;

    public static void main(String[] args) {
        writeFile();
    }

    public static void writeFile() {

        // TODO: fel kell tölteni a DFA objektumot
        List<String> atmenetLista = userAutFileReader("pelda.txt"); // TODO: módosításra szorul
        //System.out.println(atmenetLista);

        regexp = "(aba)*a|b";
//        System.out.println("Welcome. Please enter a regexp.");
//        System.out.println("NOTE:\n" +
//                " * Valid characters:\n" +
//                " *      Use any symbol rather than '|', '*', '+', '?', '^', '.'\n" +
//                " *      You MUST use ε in your expression for representation of an empty word. (Just copy it from here) \n" +
//                "Enter your regexp after this line:");
//        Scanner sc = new Scanner(System.in);
//
//        regexp = sc.nextLine();
        afn = new AFN(regexp);

        try {
            // TODO: itt hívjuk meg: itt lesz NFA-ból DFA
            Transformation transformation = new Transformation(afn.getTransitionsList(), afn.getSymbolList(),
                    afn.getFinalStates(), afn.getInitialState());

            dfa = new DFA(transformation.getDfaTable(), transformation.getDfaStates(),
                    transformation.getDfaStatesWithNumbering(), transformation.getSymbolList());

            // TODO: nem minimális DFA
            PrintWriter dfaWriter = new PrintWriter("DFA.txt", "UTF-8");
            dfaWriter.println("TRANSITIONS LIST: "+dfa.getTransitionsList());
            dfaWriter.println("INITIAL STATE: "+dfa.getInitialStates());
            dfaWriter.println("FINAL STATE(S): "+dfa.getFinalStates());
            dfaWriter.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    private static List<String> userAutFileReader (String filePath) {

        List<String> firstLineList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();

            if (firstLine != null) {
                //List<String> firstLineList = new ArrayList<>();
                String[] elements = firstLine.split(","); // Változtasd meg a szeparátort a szükségnek megfelelően

                for (String element : elements) {
                    firstLineList.add(element.trim());
                }

                System.out.println("Első sor elemei: " + firstLineList);
            } else {
                System.out.println("A fájl üres.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return firstLineList;
    }

}
