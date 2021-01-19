public void Add(T data)
{
    Node<T> newNode = addBST(data);
    Node<T> uncle;

    while ((newNode != root) && (newNode.parent.isBlack == false))
    {
        if (newNode.parent == newNode.parent.parent.left)
        {
            uncle = newNode.parent.parent.right;

            //uncle is red
            if (uncle.isBlack == false)
            {
                newNode.parent.isBlack = true;
                uncle.isBlack = true;
                newNode.parent.parent.isBlack = false;
                newNode = newNode.parent.parent;
                continue;
            }

            //triangle case
            if (newNode == newNode.parent.right)
            {
                newNode = newNode.parent;
                rotateLeft(newNode.right, newNode);
            }

            //linear case
            newNode.parent.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotateRight(newNode.parent, newNode.parent.parent);
            break;
        }
        else
        {
            uncle = newNode.parent.parent.left;

            if (uncle.isBlack == false)
            {
                newNode.parent.isBlack = true;
                uncle.isBlack = true;
                newNode.parent.parent.isBlack = false;
                newNode = newNode.parent.parent;
                continue;
            }

            if (newNode == newNode.parent.left)
            {
                newNode = newNode.parent;
                rotateRight(newNode.left, newNode);
            }

            newNode.parent.isBlack = true;
            newNode.parent.parent.isBlack = false;
            rotateLeft(newNode.parent, newNode.parent.parent);
            break;
        }
    }

    root.isBlack = true;
}