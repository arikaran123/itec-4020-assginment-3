package com.howtodoinjava.demo.lucene.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ParseInputFiles {

    public static void main(String[] args) throws IOException {
        File inputDir = new File("inputFiles");
        File outputDir = new File("indexedFiles");

        // Create output directory if it doesn't exist
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        // Process each file in the input directory
        for (File file : inputDir.listFiles()) {
            String filename = file.getName();
            if (filename.endsWith(".txt")) {
                // Create output file with same name in output directory
                File outputFile = new File(outputDir, filename);
                outputFile.createNewFile();

                // Open input and output file readers/writers
                BufferedReader reader = new BufferedReader(new FileReader(file));
                FileWriter writer = new FileWriter(outputFile);

                // Write top tag
                writer.write("<top>\n\n");

                // Parse each line and write relevant information in the desired format
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("<num>")) {
                        writer.write(line + "\n");
                    } else if (line.startsWith("<title>")) {
                        writer.write(line + "\n\n");
                    } else if (line.startsWith("<desc>")) {
                        writer.write(line + "\n");
                    } else if (line.startsWith("<narr>")) {
                        writer.write(line + "\n\n");
                    }
                }

                // Write closing top tag
                writer.write("</top>");

                // Close input and output file readers/writers
                reader.close();
                writer.close();
            }
        }
    }
}