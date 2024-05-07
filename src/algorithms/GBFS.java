package src.algorithms;

import java.util.*;

import src.utils.Dictionary;
import src.utils.Node;
import src.utils.Result;


public class GBFS extends Search{
    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        // Initialize prioqueue based on the heuristic cost
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        
        // Storing heuristic cost of a word
        Map<String,Integer> heuristicsMap = new HashMap<>();

        // Inserting the first word to queue
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(startWord,null,getHeuristic(startWord, endWord)));

        // Keep searching until queue is empty
        while (!queue.isEmpty()) {
            // Getting top of queue
            Node currNode = queue.poll();

            // Get the word
            String currWord = currNode.getWord();

            // If the node expanded is the goal return the path and also numbers of visited node
            if (currWord.equals(endWord))
            {
                // Mark the node as visited
                visited.add(currWord);
                return new Result(getPath(currNode),visited.size());
            }
            
            // If the node has not been expanded yet
            if (!visited.contains(currWord))
            {
                 // Mark so it is expanded
                visited.add(currWord);

                // Get the child nodes from the mapped dictionary
                List<String> children = dictionary.getDictionary().get(currWord);
                if (children != null)
                {

                    for (String child :children)
                    {
                        // Only add the child to queue if it has not yet been expanded
                        if (!visited.contains(child))
                        {
                            int heuristicCost;
                            // Check if the heuristic has already been searched before
                            if (heuristicsMap.containsKey(child))
                            {
                                heuristicCost = heuristicsMap.get(child); 
                            } else
                            {
                                heuristicCost = getHeuristic(child, endWord);
                                heuristicsMap.put(child, heuristicCost);
                            }
                            // Adding the child to the queue
                            queue.add(new Node(child, currNode, heuristicCost));
                        }
                    }
                }
            }
        }
        // If not found return empty list of solutions
        return new Result(new ArrayList<>(), visited.size());
    }

    // Method for getting the heuristic
    private int getHeuristic(String startWord, String endWord)
    {
        // Difference of char in a certain position with the endword
        int difference = 0;
        for (int i = 0; i < startWord.length(); i++) {
            if (startWord.charAt(i) != endWord.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }
    
}