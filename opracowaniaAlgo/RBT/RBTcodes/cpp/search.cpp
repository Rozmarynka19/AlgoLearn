
	//Returns pointer at node with the given key if succeed or NULL if failed
	Node<T>* Find(T data)
	{
		Node<T>* node = root;

		while ((node != &blackSentinelNode) && (node->data != data))
		{
			if (data > node->data) node = node->right;
			else node = node->left;
		}
		if (node == &blackSentinelNode) return NULL;

		return node;
	}