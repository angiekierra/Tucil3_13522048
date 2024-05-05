package src.utils;

import java.io.*;
import java.util.*;

// Class for mapped dictionary
public class Dictionary {
    private Map<String, List<String>> dictionary;

    // Load dictionary from txt to map
    public Dictionary(String filePath) {
        dictionary = new HashMap<>();
        loadDictionary(filePath);
    }

    public Map<String, List<String>> getDictionary() {
        return dictionary;
    }

    // Method for reading the txt and make into map
    public void loadDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(" : ");
                String word = parts[0];
                List<String> children = new ArrayList<>(Arrays.asList(parts[1].substring(1, parts[1].length() - 1).split(", ")));
                dictionary.put(word, children);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for checking a word is in the dictionary or not
    public boolean validWord(String word) {
        return dictionary.containsKey(word);
    }
}
