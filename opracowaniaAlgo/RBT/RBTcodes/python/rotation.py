#Rotates child onto parent; child is right parent's child
def rotateLeft(child, parent):
  grandparent = None

  if child != self.blackSentinelNode:
    grandparent = parent.parent
    parent.right = child.left
    if parent.right != self.blackSentinelNode:
      parent.right.parent = parent

    child.left = parent
    child.parent = grandparent
    parent.parent = child

    if grandparent != self.blackSentinelNode:
      if grandparent.right == parent:
        grandparent.right = child
      else:
        grandparent.left = child
    else:
      self.root = child

#Rotates child onto parent; child is left parent's child
def rotateRight(child, parent):
  grandparent = None

  if child != self.blackSentinelNode:
    grandparent = parent.parent
    parent.left = child.right
    if parent.left != self.blackSentinelNode:
      parent.left.parent = parent

    child.right = parent
    child.parent = grandparent
    parent.parent = child

    if grandparent != self.blackSentinelNode:
      if grandparent.right == parent:
        grandparent.right = child
      else:
        grandparent.left = child
    else:
      self.root = child