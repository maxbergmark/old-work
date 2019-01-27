from random import randint

class TreeNode():
    def __init__(self, item = None, left = None, right = None):
        self.value = item
        self.left = left
        self.right = right
        
    def remove(self, n, parent = None):

        node, parent = self.exists(n)
        if node != None:
            if node.left == None and node.right == None:
                if parent.left == node:
                    parent.left = None
                else:
                    parent.right = None
                del node
            elif bool(node.left == None) ^ bool(node.right == None):
                
                if node.left:
                    temp = node.left
                else:
                    temp = node.right
                if parent:
                    if parent.left == node:
                        parent.left = temp
                    else:
                        parent.right = temp
                else:
                    
                    node = temp
                del node
            else:
                rand = randint(0,1)
                parent = node
                if rand == 0:

                    child = node.right
                    while child.left:
                        parent = child
                        child = child.left
                    node.value = child.value
                    if parent.left == child:
                        parent.left = child.right
                    else:
                        parent.right = child.right
                else:
                    child = node.right
                    while child.left:
                        parent = child
                        child = child.left
                    node.value = child.value
                    if parent.left == child:
                        parent.left = child.right
                    else:
                        parent.right = child.right

        
    def exists(self, item, parent = None):

            if item < self.value:
                
                if self.left == None:
                    return None, None

                return self.left.exists(item, self)
                
            elif item > self.value:
                if self.right == None:
                    return None, None

                return self.right.exists(item, self)

            else:
                return self, parent
            
        


class Bintree:

    def __init__(self):
        self.top = None


    def put(self, item):
        if self.top == None:
            self.top = TreeNode(item)
            return True
        else:
            temp = self.top
            
            while temp != None:
                if item < temp.value:
                    if temp.left == None:
                        temp.left = TreeNode(item)
                        return True
                    else:
                        temp = temp.left
                elif item > temp.value:
                    if temp.right == None:
                        temp.right = TreeNode(item)
                        return True
                    else:
                        temp = temp.right

                else:
                    return False
        
    def exist1(self, item):
        if self.isempty():
            return False
        temp = self.top
        
        while (temp.right != None) or (temp.left != None):

            if temp.value == item:
                return True
            if item < temp.value:
                if temp.left != None:
                    temp = temp.left
                else:
                    return False
            elif item > temp.value:
                if temp.right != None:
                    temp = temp.right
                else:
                    return False

        return False
            
    def exists(self, item, parent = None):

        return self.top.exists(item)

    def isempty(self):
        if self.top == None:
            return True
        else:
            return False


    def height(self):
        if self.top == None:
            return 0
        else:
            return self.heights(self.top)

    def heights(self, node = None):
        leftHeight = 1
        if node.left != None:
            leftHeight += self.heights(node.left)    
        rightHeight = 1
        if node.right != None:
            rightHeight += self.heights(node.right)
        

        return max(leftHeight, rightHeight)        
            
    def printtree(self):
        print(self.prints(self.top))
        #return self.prints(self.top)

    def prints(self, node = None):
        if node == None:
            return []
        else:
            return self.prints(node.left) + [node.value] + self.prints(node.right)

    def minimum(self):
        
        if self.top.value == None:
            return None
        elif self.top.left == None:
            return self.top.value
        temp = self.top.left
        while temp.left != None:
            temp = temp.left
        return temp.value
 

    def maximum(self):
        
        if self.top.value == None:
            return None
        elif self.top.right == None:
            return self.top.value
        temp = self.top.right
        while temp.right != None:
            temp = temp.right
        return temp.value

    def remove(self, n):
        if self.top.value == n:
            if (bool(self.top.left) ^ bool(self.top.right)):
                if self.top.left:
                    self.top = self.top.left
                elif self.top.right:
                    self.top = self.top.right
            elif not (self.top.left or self.top.right):
                self.top = None
            else:
                self.top.remove(n)
        else:
            self.top.remove(n)
