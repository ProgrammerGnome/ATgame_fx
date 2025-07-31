package org.example.atgame.NFAtoDFA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    static int start; //stores the id of the start state
    static Set<String> inputSymbols; // stores the set of input symbols
    static State[] dfa; //stores all the states data
    static Group final_states; //group of final states
    static Group non_final_states; //group of non-final states

    public static void input(String file) throws IOException {
        BufferedReader scan = null;
        try {
            scan = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert scan != null;
        int n = Integer.parseInt(scan.readLine());
        dfa = new State[n];
        for (int i = 0; i < n; i++) {
            dfa[i] = new State(i);
        }

        String temp;
        inputSymbols = new HashSet<>();

        temp = scan.readLine();
        start = Integer.parseInt(temp.substring(6));

        temp = scan.readLine();
        while (!temp.startsWith("final")) {
            StringTokenizer st = new StringTokenizer(temp);

            int from = Integer.parseInt(st.nextToken());
            String at = st.nextToken();
            int to = Integer.parseInt(st.nextToken());
            dfa[from].put(at, dfa[to]);
            inputSymbols.add(at);
            temp = scan.readLine();
        }


        final_states = new Group(0);

        StringTokenizer st = new StringTokenizer(temp);
        st.nextToken();
        while (st.hasMoreTokens()) {
            final_states.add(dfa[Integer.parseInt(st.nextToken())]);
        }

    }

}
