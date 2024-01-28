package org.example.atgame.GlueComponents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileComparator {

    public boolean compareFiles(String filePath1, String filePath2) {
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

}
