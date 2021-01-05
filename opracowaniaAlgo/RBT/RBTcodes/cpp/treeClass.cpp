template <class T>
class RedBlackTree
{
private:
	Node<T> blackSentinelNode;
	Node<T>* root;
	int nodeCounter;

	//Rotates child onto parent; child is right parent's child
	void rotateLeft(Node<T>* child, Node<T>* parent);

	//Rotates child onto parent; child is left parent's child
	void rotateRight(Node<T> * child, Node<T> * parent);

public:
	RedBlackTree()
	{
		nodeCounter = 0;

		blackSentinelNode.left = &blackSentinelNode;
		blackSentinelNode.right = &blackSentinelNode;
		blackSentinelNode.parent = &blackSentinelNode;
		blackSentinelNode.isBlack = true;

		root = &blackSentinelNode;
	}
	~RedBlackTree()
	{
		ReleaseNodes(root);
	}

	void Add(T data);

	//Returns pointer at node with the given key if succeed or NULL if failed
	Node<T>* Find(T data);

    void Delete(T data);

    Node<T>* getSuccessor(Node<T>* node)
}