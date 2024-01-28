package org.example.atgame.GlueComponents;

import java.io.*;
import java.util.*;

public class TransitionConverter {

    public static void main(String[] args) {
//        try {
//            convertFile("sampleFSM/UserAutomata.txt", "sampleFSM/ConvertedUserAutomata.txt");
//            System.out.println("Conversion completed successfully.");
//        } catch (IOException ignored) {}
    }

    public void convertFile(String inputFile, String outputFile) throws IOException {
        List<String> lines = readLinesFromFile(inputFile);

        List<String> transitions = getTransitions(lines);
        Set<String> initialState = getInitialState(lines);
        Set<String> finalStates = getFinalStates(lines);

        List<String> convertedLines = new ArrayList<>();
        List<String> convertedLines_v2 = new ArrayList<>();

        convertedLines.add(transitions.size() + "");

        convertedLines.add("start " + initialState.iterator().next());

        convertedLines.addAll(transitions);

        convertedLines_v2.add("final");
        convertedLines_v2.addAll(finalStates);

        writeLinesToFile(outputFile, convertedLines);
        writeLinesToFile_v2(outputFile, convertedLines_v2);

    }

    private static List<String> getTransitions(List<String> lines) {
        String transitionsList = lines.get(0).replace("TRANSITIONS LIST: [", "").replace("]", "").replace("- ", "").replace("q", "");
        return Arrays.asList(transitionsList.split(", "));
    }

    private static Set<String> getInitialState(List<String> lines) {
        String initialState = lines.get(1).replace("INITIAL STATE: [", "").replace("]", "").replace("q","");
        return Collections.singleton(initialState);
    }

    private static Set<String> getFinalStates(List<String> lines) {
        String finalStatesList = lines.get(2).replace("FINAL STATE(S): [", "").replace("]", "").replace("q","");
        return new HashSet<>(Arrays.asList(finalStatesList.split(", ")));
    }

    private static List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static void writeLinesToFile(String filename, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void writeLinesToFile_v2(String filename, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (String line : lines) {
                writer.write(line+" ");
            }
        }
    }

}
