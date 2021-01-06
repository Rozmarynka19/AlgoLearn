def Add(data):
    newNode = self.addBST(data)
    uncle = None

    while newNode != self.root and newNode.parent.isBlack == False:
        if newNode.parent == newNode.parent.parent.left:
            uncle = newNode.parent.parent.right

            #uncle is red
            if uncle.isBlack == False:
                newNode.parent.isBlack = True
                uncle.isBlack = True
                newNode.parent.parent.isBlack = False
                newNode = newNode.parent.parent
                continue

            #triangle case
            if newNode == newNode.parent.right:
                newNode = newNode.parent
                self.rotateLeft(newNode.right, newNode)

            #linear case
            newNode.parent.isBlack = True
            newNode.parent.parent.isBlack = False
            self.rotateRight(newNode.parent, newNode.parent.parent)
            break
        else:
            uncle = newNode.parent.parent.left

            if uncle.isBlack == False:
                newNode.parent.isBlack = True
                uncle.isBlack = True
                newNode.parent.parent.isBlack = False
                newNode = newNode.parent.parent
                continue

            if newNode == newNode.parent.left:
                newNode = newNode.parent
                self.rotateRight(newNode.left, newNode)

            newNode.parent.isBlack = True
            newNode.parent.parent.isBlack = False
            self.rotateLeft(newNode.parent, newNode.parent.parent)
            break

    root.isBlack = True