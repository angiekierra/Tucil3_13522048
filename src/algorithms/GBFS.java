package src.algorithms;

import java.util.*;

import src.utils.Dictionary;
import src.utils.Node;
import src.utils.Result;


public class GBFS extends Search{
    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Map<String,Integer> heuristicsMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startWord,null,getHeuristic(startWord, endWord)));


        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String currWord = currNode.getWord();

            if (currWord.equals(endWord))
            {
                visited.add(currWord);
                return new Result(getPath(currNode),visited.size());
            }
            

            if (!visited.contains(currWord))
            {
                visited.add(currWord);
                List<String> children = dictionary.getDictionary().get(currWord);
                if (children != null)
                {

                    for (String child :children)
                    {
                        if (!visited.contains(child))
                        {
                            int heuristicCost;
    
                            if (heuristicsMap.containsKey(child))
                            {
                                heuristicCost = heuristicsMap.get(child); 
                            } else
                            {
                                heuristicCost = getHeuristic(child, endWord);
                                heuristicsMap.put(child, heuristicCost);
                            }
                            queue.add(new Node(child, currNode, heuristicCost));
                        }
                    }
                }
            }
        }
        return new Result(new ArrayList<>(), visited.size());
    }


    private int getHeuristic(String startWord, String endWord)
    {
        int difference = 0;
        for (int i = 0; i < startWord.length(); i++) {
            if (startWord.charAt(i) != endWord.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }
    
}