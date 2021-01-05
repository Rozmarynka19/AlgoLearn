public class Node<T extends Comparable<T>>
{}
    public Node<T> parent,left,right;
    public T data;
    public boolean isBlack;
    
    public Node() {}
    public Node(T data)
    {
        this.data=data;
        this.isBlack=false;
    }


}
