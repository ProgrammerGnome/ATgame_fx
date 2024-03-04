package org.example.atgame.RegexToDFA;

//import org.example.atgame.RegexToDFA.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UserNFAtoDFA {
    public static String regexp;
    public static AFN afn;
    public static DFA dfa;

    public void writeFile() {

        regexp = "(aba)*.a|b";
        // (aba)*a|b

        /*
          Valid characters:
               Use any symbol rather than '|', '*', '+', '?', '^', '.'
               You MUST use ε in your expression for representation of an empty word. (Just copy it from here)
         */
        afn = new AFN(regexp);

        try {
            // TODO: itt hívjuk meg: itt lesz NFA-ból DFA
            Transformation transformation = new Transformation(afn.getTransitionsList(), afn.getSymbolList(),
                    afn.getFinalStates(), afn.getInitialState());

            dfa = new DFA(transformation.getDfaTable(), transformation.getDfaStates(),
                    transformation.getDfaStatesWithNumbering(), transformation.getSymbolList());

            // TODO: nem minimális DFA
            PrintWriter dfaWriter = new PrintWriter("sampleFSM/machineDFA.txt", "UTF-8");
            dfaWriter.println("TRANSITIONS LIST: " + dfa.getTransitionsList());
            dfaWriter.println("INITIAL STATE: " + dfa.getInitialStates());
            dfaWriter.println("FINAL STATE(S): " + dfa.getFinalStates());
            dfaWriter.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
