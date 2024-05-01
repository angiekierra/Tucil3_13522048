import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
    private Map<Integer, Set<String>> dictionary;

    public Dictionary(String filePath) {
        dictionary = new HashMap<>();
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                int length = line.length();
                Set<String> wordSet = dictionary.getOrDefault(length, new HashSet<>());
                wordSet.add(line);
                dictionary.put(length, wordSet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Set<String>> getDictionary() {
        return dictionary;
    }

    public boolean validWord(String word) {
        int length = word.length();
        Set<String> wordSet = dictionary.get(length);
        return wordSet != null && wordSet.contains(word);
    }

    // public static void main(String[] args) {
    //     Dictionary dictionary = new Dictionary("dictionary.txt");
    //     Map<Integer, Set<String>> wordGroups = dictionary.getDictionary();
    //     for (Map.Entry<Integer, Set<String>> entry : wordGroups.entrySet()) {
    //         int length = entry.getKey();
    //         if (length == 3)
    //         {
    //             Set<String> words = entry.getValue();
    //             System.out.println("Words with length " + length + ": " + words);

    //         }
    //     }

    //     System.out.println(dictionary.validWord("bacot"));
    //     System.out.println(dictionary.validWord("beast"));
    // }
}
