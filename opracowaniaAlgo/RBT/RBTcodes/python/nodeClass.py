class Node:
    def __init__(self,data):
        self.data = data
        self.isBlack = False
        self.parent = None
        self.left = None
        self.right = None