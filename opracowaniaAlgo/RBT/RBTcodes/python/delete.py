def Delete(data):
   X = self.Find(data)
   W = None
   Y = None
   Z = None

  if X.left == self.blackSentinelNode or X.right == self.blackSentinelNode:
       Y = X
  else:
       Y = self.getSuccessor( X )

  if Y.left != self.blackSentinelNode:
       Z = Y.left
  else:
       Z = Y.right

  Z.parent = Y.parent

  if Y.parent == self.blackSentinelNode:
      root = Z
  elif Y == Y.parent.left:
      Y.parent.left  = Z
  else:
       Y.parent.right = Z

  if Y != X: 
      X.data = Y.data

  if Y.isBlack:
    while Z != root and Z.isBlack:
      if Z == Z.parent.left:
        W = Z.parent.right

        if not W.isBlack:
          W.isBlack = True
          Z.parent.isBlack = False
          self.rotateLeft(Z.parent, Z.parent.parent)
          W = Z.parent.right

        if W.left.isBlack and W.right.isBlack:
          W.isBlack = False
          Z = Z.parent
          continue

        if W.right.isBlack:
          W.left.isBlack = True
          W.isBlack = False
          self.rotateRight(W, W.parent)
          W = Z.parent.right

        W.isBlack = Z.parent.isBlack
        Z.parent.isBlack = W.right.isBlack = True
        self.rotateLeft(Z.parent, Z.parent.parent)
        Z = self.root
      else:
        W = Z.parent.left

        if not W.isBlack:
          W.isBlack = True
          Z.parent.isBlack = False
          self.rotateRight(Z.parent, Z.parent.parent)
          W = Z.parent.left

        if W.left.isBlack and W.right.isBlack:
          W.isBlack = False
          Z = Z.parent
          continue

        if W.left.isBlack:
          W.right.isBlack = True
          W.isBlack = False
          self.rotateLeft(W, W.parent)
          W = Z.parent.left

        W.isBlack = Z.parent.isBlack
        Z.parent.isBlack = True
        W.left.isBlack = True
        self.rotateRight(Z.parent, Z.parent.parent)
        Z = self.root

  Z.isBlack = True

  Y = None