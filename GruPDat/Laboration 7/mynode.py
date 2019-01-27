class Node():
    def __init__(self, item = None, nex = None):
        self.item = item
        self.next = nex


class Queue:

    def __init__(self):
        self.back = None


    def put(self, item):
        node = Node(item, self.back)
        self.back = node

    def get(self):
        if self.back:

            front = self.back
            if self.back.next:

                while front.next:
                    previous = front
                    front = front.next

                previous.next = None
                return front.item
            else:
                self.back = None
                return front.item
        else:
            return None

    def isempty(self):
        return self.back == None
