package org.example.atgame.DFAMinimization.fa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DfaReader {

    private final DFA dfa;

    public DfaReader() {
        this.dfa = new DFA();
    }

    public void readDFA_main(String inputFile, String outputFile) {
        readDFA(selectFile(inputFile, outputFile));
    }

    public void readDFA(String path) {
        try {
            Scanner faReader = new Scanner(new File(path));
            int n = Integer.parseInt(faReader.nextLine());
            this.dfa.setDfa(new State[n]);
            for (int i = 0; i < n; i++) { //add id to every state of dfa
                this.dfa.partitionToDFA()[i] = new State(i);
            }
            while (faReader.hasNextLine()) {
                String data = faReader.nextLine();
                String[] temp = data.split(" ");
                if (!data.contains("start") && !data.contains("final")) { //add letters to alphabet
                    this.dfa.addToAlphabet(temp[1]);
                }
                if (data.startsWith("start")) {
                    String[] initialStates = data.substring(6).split(" ");
                    for (String initialState : initialStates) {
                        dfa.setInitialState(this.dfa.partitionToDFA()[Integer.parseInt(initialState)]);
                    }
                } else if (data.startsWith("final")) {
                    String[] finalStates = data.substring(6).split(" ");
                    for (String finalState : finalStates) {
                        dfa.addFinalState(this.dfa.partitionToDFA()[Integer.parseInt(finalState)]);
                        this.dfa.getNonFinalStates().remove(this.dfa.partitionToDFA()[Integer.parseInt(finalState)]);
                    }
                } else {
                    int from = Integer.parseInt(temp[0]);
                    int to = Integer.parseInt(temp[2]);
                    this.dfa.partitionToDFA()[to].addTransitionFrom(temp[1], this.dfa.partitionToDFA()[from]);
                    this.dfa.partitionToDFA()[from].addTransitionTo(temp[1], this.dfa.partitionToDFA()[to]);
                    this.dfa.addNonFinalState(this.dfa.partitionToDFA()[from]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String selectFile(String inputFile, String outputFile) {
        //String pathToFile = "sampleFSM/DFA_Input_2.txt";
        Scanner scanner = new Scanner(System.in);
        while (inputFile.isEmpty()) {
            System.out.println("enter valid absolute path to file  ");
            inputFile = scanner.nextLine();
            if (!isValidPath(inputFile)) {
                System.out.println("invalid path to file ");
                inputFile = "";
            }
        }
        return inputFile;
    }

    public static boolean isValidPath(String pathToFile) {
        File tempFile = new File(pathToFile);
        boolean exists = tempFile.exists();
        boolean isTextFile = (pathToFile.endsWith(".txt"));
        return (exists && isTextFile);
    }


    public static boolean containsUnreachableState(DFA dfa) {
        return dfa.containsUnreachableState();
    }

    public DFA getDfa() {
        return this.dfa;
    }
}
