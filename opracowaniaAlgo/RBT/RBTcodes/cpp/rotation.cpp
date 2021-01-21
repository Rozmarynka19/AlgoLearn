//Rotates child onto parent; child is right parent's child
void rotateLeft(Node<T>* child, Node<T>* parent)
{
	Node<T>* grandparent;

	if (child != &blackSentinelNode)
	{
		grandparent = parent->parent;
		parent->right = child->left;
		if (parent->right != &blackSentinelNode) parent->right->parent = parent;

		child->left = parent;
		child->parent = grandparent;
		parent->parent = child;

		if (grandparent != &blackSentinelNode)
		{
			if (grandparent->right == parent) grandparent->right = child;
			else grandparent->left = child;
		}
		else root = child;
	}
}

	//Rotates child onto parent; child is left parent's child
void rotateRight(Node<T> * child, Node<T> * parent)
{
	Node<T>* grandparent;

	if (child != &blackSentinelNode)
	{
		grandparent = parent->parent;
		parent->left = child->right;
		if (parent->left != &blackSentinelNode) parent->left->parent = parent;

		child->right = parent;
		child->parent = grandparent;
		parent->parent = child;

		if (grandparent != &blackSentinelNode)
		{
			if (grandparent->right == parent)  grandparent->right = child;
			else grandparent->left = child;
		}
		else root = child;
	}
}