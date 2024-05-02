public class Node {
    private String word;
    private Node parent;
    private int price;

    public Node(String word, Node parent, int price)
    {
        this.word = word;
        this.parent = parent;
        this.price = price;
    }

    public String getWord()
    {
        return this.word;
    }

    public int getPrice()
    {
        return this.price;
    }

    public Node getParent()
    {
        return this.parent;
    }


}
