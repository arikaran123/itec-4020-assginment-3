package com.howtodoinjava.demo.lucene.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Preprocessor {

    public static void main(String[] args) throws IOException {
        String[] inputPaths = {"C:\\Users\\spyma\\Downloads\\LuceneDemo\\data\\WT01",
                               "C:\\Users\\spyma\\Downloads\\LuceneDemo\\data\\WT02",
                               "C:\\Users\\spyma\\Downloads\\LuceneDemo\\data\\WT03"};
        String outputPath = "C:\\Users\\spyma\\Downloads\\LuceneDemo\\inputFiles";
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        for (String inputPath : inputPaths) {
            File inputDir = new File(inputPath);
            String outputSubDir = inputDir.getName();
            String outputDirPath = outputPath + File.separator + outputSubDir;
            File outputSubDirFile = new File(outputDirPath);
            if (!outputSubDirFile.exists()) {
                outputSubDirFile.mkdirs();
            }
            for (File file : inputDir.listFiles()) {
                String fileName = file.getName();
                String fileContent = extractTextFromFile(file);
                String outputFilePath = outputDirPath + File.separator + fileName.replaceAll("\\.file$", "") + ".txt";
                saveTextToFile(fileContent, outputFilePath);
            }
        }
        System.out.println("Files processed successfully.");
    }

    private static String extractTextFromFile(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        }
        Document document = Jsoup.parse(fileContent.toString());
        return document.text();
    }

    private static void saveTextToFile(String text, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(text);
        }
    }
}
