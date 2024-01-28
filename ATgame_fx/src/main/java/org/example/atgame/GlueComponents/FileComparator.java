package org.example.atgame.GlueComponents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileComparator {

    public /*static*/ boolean compareFiles(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String line1, line2;

            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                // Ellenőrizd, hogy a két sor azonos-e
                if (!line1.equals(line2)) {
                    return false;
                }
            }

            // Ellenőrizd, hogy mindkét fájl ugyanannyi sorral rendelkezik
            return reader1.readLine() == null && reader2.readLine() == null;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        String filePath1 = "output.txt";
//        String filePath2 = "output2.txt";
//
//        boolean areFilesEqual = compareFiles(filePath1, filePath2);
//
//        if (areFilesEqual) {
//            System.out.println("A két fájl tartalma azonos.");
//        } else {
//            System.out.println("A két fájl tartalma különbözik.");
//        }
//    }
}
