template <class T>
class Node
{
	template <class T> friend class RedBlackTree;

	Node<T>* parent, * left, * right;
	T data;
	bool isBlack;

public:
	Node() {}
	Node(T data)
	{
		this->data = data;
		this->isBlack = false;
	}
};