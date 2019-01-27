class Node:

    def __init__(self):
        self.value = False
        self.a = False
        self.b = False
        self.c = False

class Tree:

    def __init__(self):
        self.top = Node()

    def addword(self, word):
        for i in word:
            if i == 'a':
                self.top.a = True
