import java.util.*;



public class UCS extends Search{

    public Result findSolution(String startWord, String endWord, Dictionary dictionary)
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
                        queue.offer(new Node(child, currNode, cost));
                    }
                }
            }
        }
        return new Result(new ArrayList<>(), visited.size());

    }
}