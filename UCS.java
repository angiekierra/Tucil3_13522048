import java.util.*;


public class UCS extends Search{

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
                        int cost = currNode.getPrice() + 1;
                        queue.add(new Node(child, currNode, cost));
    
                    }
                }
            }
        }
        return new ArrayList<>();

    }
}