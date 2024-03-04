package org.example.atgame.DFAMinimization;

import org.example.atgame.DFAMinimization.algorithms.Watson;
import org.example.atgame.DFAMinimization.fa.DFA;
import org.example.atgame.DFAMinimization.fa.DfaReader;

import java.util.Scanner;

public class MainMini {

//    public static void main(String[] args) throws IOException {
//        menu();
//    }

    public void menu(String inputFile, String outputFile) {
//        while (true) {
        System.out.println("\nWould you like to: \n1)generate new dfa(s) \n2)read existing ");
//        int option = 2;
//        Scanner scanner = new Scanner(String.valueOf(option));
        menu2(inputFile,outputFile);
//            System.out.println("\nWould you like to continue? \n[Y/y] - yes \n[N/n] - no ");
//            char c = 'e';
//            c = scanner.next().charAt(0);
//            while (!(c == 'n' || c == 'N' || c == 'y' || c == 'Y')) {
//                System.out.println("invalid input");
//                System.out.println("\nWould you like to continue? \n[Y/y] - yes \n[N/n] - no  ");
//                c = scanner.next().charAt(0);
//            }
//            if (c == 'n' || c == 'N') {
//                break;
//            }
//        }
    }

    public void menu2(String inputFile, String outputFile) {
        DfaReader dfaReader = new DfaReader();
        dfaReader.readDFA_main(inputFile,outputFile);
        DFA dfa = dfaReader.getDfa();
        int option = option();
        minimization(option, dfa, outputFile);
    }

//    public static void menu1() {
//        Scanner scanner = new Scanner(System.in);
//        String pathToFile = "sampleFSM/dfa.txt";
//        //System.out.println("enter the file directory");
//        while (pathToFile.isEmpty()) {
//            pathToFile = scanner.nextLine();
//            boolean b = checkDirectory(pathToFile);
//            if (!b) {
//                b = createDirectory(pathToFile);
//                if (!b) {
//                    pathToFile = "";
//                }
//            }
//        }
//        System.out.println("enter the number of files to generate(how many automata you want to generate): ");
//        int i = Integer.parseInt(scanner.nextLine());
//        System.out.println("enter the number of states: ");
//        int n = Integer.parseInt(scanner.nextLine());
//        System.out.println("enter the alphabet size: ");
//        int size = Integer.parseInt(scanner.nextLine());
//        Set<String> alphabet = newAlphabet(size);
//        DFA[] dfas = new DFA[i];
//        for (int j = 0; j < i; j++) {
//            DfaGenerator dfaGenerator = new DfaGenerator(pathToFile, n, alphabet, i);
//            dfas[j] = dfaGenerator.getDfa();
//        }
//        int option;
//        for (int j = 0; j < dfas.length; j++) {
//            System.out.println(" .....Minimizing " + (j + 1) + ". automaton..... ");
//            option = option();
//            minimization(option, dfas[j]);
//        }
//    }


//    public static boolean checkDirectory(String path) {
//        boolean exists = false;
//        File directory = new File(path);
//        if (directory.exists()) {
//            exists = true;
//        }
//        return exists;
//    }
//
//    public static boolean createDirectory(String path) {
//        File file = new File(path);
//        boolean bool = file.mkdirs();
//        if (bool) {
//            System.out.println("Directory created successfully");
//            return true;
//        }
//        System.out.println("Sorry couldn't create specified directory");
//        return false;
//    }

//    public boolean useAnotherAlgorithm() {
//        char c = 'e';
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Would you like to try another algorithm for this dfa? \n[Y/y] - yes \n[N/n] - no  ");
//        c = scanner.next().charAt(0);
//        while (!(c == 'n' || c == 'N' || c == 'y' || c == 'Y')) {
//            System.out.println("invalid input");
//            System.out.println("Would you like to try another algorithm for this dfa? \n[Y/y] - yes \n[N/n] - no  ");
//            c = scanner.next().charAt(0);
//        }
//        return c != 'n' && c != 'N';
//    }

    public void minimization(int option, DFA dfa, String outputFile) {
        DFA copy = dfa.copy();
        System.out.println("original DFA");
        dfa.printDFA(outputFile);
        if (option == 1) {
            Watson watson = new Watson(copy);
            watson.minimization();
            watson.toDFA().printDFA(outputFile);
        }
//        if (useAnotherAlgorithm()) {
//            option = option();
//            minimization(option, dfa);
//        }
    }

    public int option() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("choose the algorithm:\n1)Watson");
        //String line = scanner.nextLine();
        Integer line = 1;
//        while (!algorithmOptionValidation(line)) {
//            System.out.println("invalid input");
//            System.out.println("choose the algorithm:\n1)Watson");
//            line = scanner.nextLine();
//        }
        return Integer.parseInt(line.toString());
    }

//    public static boolean algorithmOptionValidation(String line) {
//        return Helper.isNumeric(line) && Integer.parseInt(line) <= 4 && Integer.parseInt(line) > 0;
//    }

//    public static Set<String> newAlphabet(int size) {
//        Set<String> a = new HashSet<String>();
//        char c;
//        for (int i = 0; i < size; i++) {
//            c = (char) (i + 'a');
//            a.add(String.valueOf(c));
//        }
//        return a;
//    }

}
