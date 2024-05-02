import java.util.*;


public class GBFS extends Search{
    public List<String> findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        System.out.println("GBFS");
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startWord,null,0));


        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String currWord = currNode.getWord();
            System.out.printf("Current word: %s\n ", currWord);
            System.out.println();

            if (currWord.equals(endWord))
            {
                System.out.println("Found!!!!");
                return getPath(currNode);
            }

            if (!visited.contains(currWord))
            {
                visited.add(currWord);
                for (String child : getChild(currWord, dictionary))
                {
                    if (!visited.contains(child))
                    {
                        int heuristicCost = getHeuristic(child, endWord);
                        System.out.printf("Heuristic %d\n", heuristicCost);
                        System.out.println();
                        queue.add(new Node(child, currNode, heuristicCost));
                    }
                }
            }
        }
        return new ArrayList<>();
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