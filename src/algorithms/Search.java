package src.algorithms;

import java.util.*;

import src.utils.Dictionary;
import src.utils.Node;
import src.utils.Result;

// Abstract class for search algorithm
public abstract class Search {

    // Abstract method for finding solution
    public abstract Result findSolution(String startWord, String endWord, Dictionary dictionary);

    // Method for converting list of nodes into lis of strings
    protected List<String> getPath(Node node) 
    {
        List<String> path = new ArrayList<>();
        while (node != null) 
        {
            path.add(0, node.getWord());
            node = node.getParent();
        }
        return path;
    }
}
