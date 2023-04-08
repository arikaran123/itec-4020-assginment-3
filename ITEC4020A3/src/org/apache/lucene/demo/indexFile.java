package org.apache.lucene.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class indexFile{
 public static void main (String[] args) throws IOException {
	try(BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Zenbook\\Desktop\\dataset\\data\\WT01\\B01"))) {
		String line;
		while ((line = br.readLine()) != null) {
			
		}
}
}
}