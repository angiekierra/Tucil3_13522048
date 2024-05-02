import java.util.*;

public abstract class Search {

    public abstract List<String> findSolution(String startWord, String endWord, Dictionary dictionary);
    
    protected List<String> getChild(String word, Dictionary dictionary) {
        List<String> children = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] chars = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String child = new String(chars);
                if (!child.equals(word) && dictionary.isIn(child, word.length())) {
                    children.add(child);
                }
            }
        }
        return children;
    }

    protected List<String> getPath(Node node) {
        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.getWord());
            node = node.getParent();
        }
        return path;
    }
}
