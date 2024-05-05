package src.utils;

import java.io.*;
import java.util.*;

public class MapDictionary {
    private Set<String> dictionaryWords;

    public MapDictionary() {
        dictionaryWords = new HashSet<>();
    }

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
    

    private List<String> getChild(String word) {
        List<String> children = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String child = new String(chars);
                if (!child.equals(word) && dictionaryWords.contains(child)) {
                    children.add(child);
                }
            }
        }
        return children;
    }
}
