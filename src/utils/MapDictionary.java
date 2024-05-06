package src.utils;

import java.io.*;
import java.util.*;

// Class for reading dictionary and mapping it into a new txt with its children nodes
public class MapDictionary {
    private Set<String> dictionaryWords;

    public MapDictionary() {
        dictionaryWords = new HashSet<>();
    }

    // Reading normal dictionary
    public void loadDictionary(String dictionaryFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                dictionaryWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mapping and creating new txt
    public void mapAndWrite(String outputFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            Map<String, List<String>> mappedDictionary = new TreeMap<>();
            for (String word : dictionaryWords) {
                List<String> children = getChild(word);
                mappedDictionary.put(word, children);
            }
            for (Map.Entry<String, List<String>> entry : mappedDictionary.entrySet()) {
                bw.write(entry.getKey() + " : " + entry.getValue().toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to get adjacent valid words 
    private List<String> getChild(String word) {
        List<String> children = new ArrayList<>();
        // Iterating through all of the characters
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();
            // Change the current character to a-z
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String child = new String(chars);
                
                // Check if changed word is in the dictionary
                if (!child.equals(word) && dictionaryWords.contains(child)) {
                    children.add(child);
                }
            }
        }
        Collections.sort(children);
        return children;
    }

    public static void main(String[] args)
    {
        MapDictionary map = new MapDictionary();
        map.loadDictionary("src/utils/dictionary.txt");
        map.mapAndWrite("mapped_sorted.txt");
    }
}
