package src.algorithms;

import java.util.*;

import src.utils.Dictionary;
import src.utils.Node;
import src.utils.Result;



public class UCS extends Search{

    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        System.out.println("UCS");
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Map<String,Integer> costMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(startWord,null,0));


        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String currWord = currNode.getWord();

            if (currWord.equals(endWord))
            {
                System.out.println("Found!!!!");
                return new Result(getPath(currNode),visited.size());
            }

            if (!visited.contains(currWord))
            {
                visited.add(currWord);
                for (String child : getChild(currWord, dictionary))
                {

                    if (!visited.contains(child))
                    {
                        int cost = currNode.getPrice() + 1;

                        if (!costMap.containsKey(child) || cost < costMap.get(child))
                        {
                            costMap.put(child,cost);
                            queue.offer(new Node(child, currNode, cost));
                        }
                    }
                }
            }
        }
        return new Result(new ArrayList<>(), visited.size());

    }
}