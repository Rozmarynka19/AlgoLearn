import gc


class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        self.prev = None


class DLL:
    def __init__(self):
        self.head = None
        self.tail = None
        self.lSize = 0

    def push_front(self, insert_data):
        new_node = Node(insert_data)
        new_node.next = self.head
        new_node.prev = None

        if self.head is not None and self.lSize != 0:
            self.head.prev = new_node

        if self.tail is None:
            self.tail = new_node

        self.head = new_node
        self.lSize += 1

    def push_back(self, insert_data):
        new_node = Node(insert_data)
        last = self.head
        if last is None:
            new_node.prev = None
            new_node.next = None
            self.head = new_node
            self.tail = new_node
        else:
            while last.next is not None:
                last = last.next
            last.next = new_node
            new_node.prev = last
            self.tail = new_node
        self.lSize += 1

    def pop_front(self):
        if self.lSize > 0:
            self.head = self.head.next
            self.lSize -= 1

    def pop_back(self):
        if self.lSize > 0:
            if self.tail.prev is None:
                self.head = None
                self.tail = None
            else:
                self.tail = self.tail.prev
                self.tail.next = None
            self.lSize -= 1

    def insert_at(self, insert_after, insert_data):
        tmp = self.head
        x = 1
        while tmp is not None:
            if x == insert_after:
                new_node = Node(insert_data)
                new_node.next = tmp.next
                new_node.prev = tmp
                tmp.next = new_node
                if new_node.next is not None:
                    tmp.next.prev = new_node
                    self.tail.prev = new_node
                break
            tmp = tmp.next
            x += 1
        self.lSize += 1

    def print_list(self):
        tmp = self.head
        print("Size: ", self.lSize)
        while tmp is not None:
            print(tmp.data, end=" ")
            tmp = tmp.next
        print()


dll = DLL()
dll.push_front(6)
dll.print_list()
dll.push_back(5)
dll.print_list()
dll.insert_at(1, 4)
dll.print_list()
dll.pop_back()
dll.print_list()
dll.pop_front()
dll.print_list()
gc.enable()
