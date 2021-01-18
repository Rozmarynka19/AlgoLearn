#Returns pointer at node with the given key if succeed or None if failed
def Find(data):
    node = self.root

    while node != self.blackSentinelNode and  node.data != data:
        if data > node.data:
             node = node.right
        else:
            node = node.left
    if node == self.blackSentinelNode:
        return None

    return node