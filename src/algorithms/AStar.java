package src.algorithms;

import java.util.*;

import src.utils.Dictionary;
import src.utils.Node;
import src.utils.Result;


public class AStar extends Search {
    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        // Initialize prioqueue based on the heuristic + actual cost
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));

        // Storing the actual cost of a word
        Map<String,Integer> costMap = new HashMap<>();

        // Storing heuristic cost of a word
        Map<String,Integer> heuristicsMap = new HashMap<>();

        // Keep track of nodes that are not expanded
        Set<String> visited = new HashSet<>();

        // Inserting the first word to queue
        queue.offer(new Node(startWord,null,0));
        costMap.put(startWord, 0);

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

                // If it has valid children
                if (children != null)
                {

                    for (String child : children)
                    {
                        // Only add the child to queue if it has not yet been expanded
                        if (!visited.contains(child))
                        {
                            
                            // Get the cost
                            int cost = costMap.get(currWord) + 1;
                            
                            // If it is the first encounter of the child and child is not in the queue
                            // Or it the cost is lower then the child already in the queue, update the cost map to the lower cost
                            // Insert the child
                            if (!costMap.containsKey(child) || cost < costMap.get(child))
                            {
                                costMap.put(child, cost);
    
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
                                
                                // Add the actual cost and the heuristic cost
                                int fn = cost + heuristicCost;

                                // Insert to queue
                                queue.offer(new Node(child, currNode, fn));
                            }
    
        
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