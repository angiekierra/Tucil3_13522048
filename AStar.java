import java.util.*;


public class AStar extends Search {
    public List<String> findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startWord,null,0));

        visited.add(startWord);

        while (!queue.isEmpty()) {
            Node currNode = queue.poll();
            String currWord = currNode.getWord();

            if (currWord.equals(endWord))
            {
                return getPath(currNode);
            }

            if (!visited.contains(currWord))
            {
                visited.add(currWord);
                for (String child : getChild(currWord, dictionary))
                {
                    if (!visited.contains(child))
                    {
                        int cost = getHeuristic(child, endWord);
                        queue.add(new Node(child, currNode, cost));
    
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