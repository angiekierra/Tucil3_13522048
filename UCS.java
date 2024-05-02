import java.util.*;



public class UCS extends Search{

    public List<String> findSolution(String startWord, String endWord, Dictionary dictionary)
    {
        System.out.println("UCS");
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPrice()));
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(startWord,null,0));


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
                        int cost = currNode.getPrice() + 1;
                        System.out.printf("Cost",cost);
                        System.out.println();
                        queue.offer(new Node(child, currNode, cost));
                    }
                }
            }
        }
        return new ArrayList<>();

    }
}