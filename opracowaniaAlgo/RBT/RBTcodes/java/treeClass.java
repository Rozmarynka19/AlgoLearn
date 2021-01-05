public class RedBlackTree<T extends Comparable<T>> {
    
    private Node<T> blackSentinelNode;
    private Node<T> root;
    private int nodeCounter;

    private void rotateLeft(Node<T> child, Node<T> parent){...}
    private void rotateRight(Node<T> child, Node<T> parent){...}

    public RedBlackTree()
    {
        nodeCounter=0;

        blackSentinelNode.left=blackSentinelNode;
        blackSentinelNode.right=blackSentinelNode;
        blackSentinelNode.parent=blackSentinelNode;
        blackSentinelNode.isBlack=true;

        root = blackSentinelNode;
    }
    public void Add(T data) {...}
    public Node<T> Find(T data) {...}
    public void Delete (T data) {...}
    public Node<T> getSuccessor(Node<T> node) {...}


}
