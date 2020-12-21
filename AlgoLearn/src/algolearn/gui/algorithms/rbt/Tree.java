package algolearn.gui.algorithms.rbt;

public interface Tree<E> extends Iterable<E> {
    public boolean insert(E e);
    public boolean delete(E e);
    public boolean search(E e);
    public void inorder();
    public void postorder();
    public void preorder();
    public int getSize();
    public boolean isEmpty();
}
