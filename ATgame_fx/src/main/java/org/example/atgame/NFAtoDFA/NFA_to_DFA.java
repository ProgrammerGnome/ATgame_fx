package org.example.atgame.NFAtoDFA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class NFA_to_DFA {

    static int start; //stores the id of the start state
    static Set<String> inputSymbols; // stores the set of input symbols
    static State dfa[]; //stores all the states data
    static Group final_states; //group of final states
    static Group non_final_states; //group of non-final states

    public void nfa_to_dfa(String inputFilePath, String outFileName) throws IOException {

        //take all the inputs from file "nfa"
        Main.input(inputFilePath);
        start = Main.start;
        inputSymbols = Main.inputSymbols;
        dfa = Main.dfa;
        final_states = Main.final_states;
        non_final_states = Main.non_final_states;

        //store the output dfa as it is stored in the file
        String output = "";
        output = output + "start " + start + "\n";

        //stores the mapping of every state with its id
        //each state is stored as a string and mapped to its id
        Map<String, Integer> map = new HashMap<>();

        //id of start state is 0
        map.put(start + "", 0);

        int idx = 1;

        LinkedList<String> queue = new LinkedList<>();
        queue.add(start + "");

        String fstate = "final";
        boolean finalState;

        while (!queue.isEmpty()) {
            //get state as an array of characters
            char state[] = queue.poll().toCharArray();

            //for all String "is" as an input symbol
            for (String is : inputSymbols) {

                String tmp = "";

                //flag to check final state
                finalState = false;

                //for each state as a character ch in the array state
                for (char ch : state) {

                    // if the current state have a transaction
                    // for the current input symbol
                    if (dfa[ch - 48].transitions.get(is) != null)

                        // concatenate all the states that can be traversed from
                        // current state at the current input symbol
                        for (State i : dfa[ch - 48].transitions.get(is)) {
                            tmp += i.id;
                            if (final_states.contains(i))
                                finalState = true;
                        }
                }

                //create a new state and map it to a unique id
                //and add it to the queue
                char array[] = tmp.toCharArray();
                Arrays.sort(array);
                tmp = new String(array);
                if (!map.containsKey(tmp)) {
                    queue.add(tmp);
                    map.put(tmp, idx++);

                    if (finalState) {
                        fstate = fstate + " " + map.get(tmp);
                    }
                }

                output = output + map.get(String.valueOf(state)) + " " + is + " " + map.get(tmp) + "\n";

            }
        }

        output = idx + "\n" + output;
        output = output + fstate + "\n";

        System.out.println(output);

        //store DFA in a file "dfa"
        PrintWriter pw = new PrintWriter(new FileWriter(outFileName));
        pw.println(output);
        pw.close();

        //change counter in header line
        //String filePath = "sampleFSM/DFA_Input_2.txt";
        try {
            // Fájl beolvasása soronként
            List<String> lines = Files.readAllLines(Paths.get(outFileName), StandardCharsets.UTF_8);

            // Ellenőrizni, hogy van-e legalább egy sor
            if (!lines.isEmpty()) {
                // Az első sor tartalmának módosítása (például növelése eggyel)
                int newValue = lines.size() - 5;
                String finalValue = lines.get(lines.size() - 3) + " ";

                // Az új érték visszaírása az első sorba
                lines.set(0, Integer.toString(newValue));
                lines.set(lines.size() - 3, finalValue);
                lines.remove(lines.size() - 1);
                lines.remove(lines.size() - 2);

                // Fájl újratöltése az elérési útvonal alapján
                Files.write(Paths.get(outFileName), lines, StandardCharsets.UTF_8);
            } else {
                System.out.println("A fájl üres.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
