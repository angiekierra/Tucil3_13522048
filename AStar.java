import java.util.*;


public class AStar extends Search {
    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        System.out.println("A-STAR");
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Map<String,Integer> costMap = new HashMap<>();
        Map<String,Integer> heuristicsMap = new HashMap<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(startWord,null,0));
        costMap.put(startWord, 0);


        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String currWord = currNode.getWord();
            System.out.printf("Current word: %s%n", currWord);
            System.out.println();

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

                        int cost = costMap.get(currWord) + 1;
                        System.out.printf("Cost %d",cost);
                        System.out.println();


                        if (!costMap.containsKey(child) || cost < costMap.get(child))
                        {
                            costMap.put(child, cost);

                            int heuristicCost;

                            if (heuristicsMap.containsKey(child))
                            {
                                heuristicCost = heuristicsMap.get(child); 
                            } else
                            {
                                heuristicCost = getHeuristic(child, endWord);
                                heuristicsMap.put(child, heuristicCost);
                            }
                            
                            int fn = cost + heuristicCost;

                            System.out.printf("F(n): %d", fn);
                            System.out.println();
                            queue.offer(new Node(child, currNode, fn));
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