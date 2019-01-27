class TreeNode():
    def __init__(self, item = None, left = None, right = None):
        self.value = item
        self.left = left
        self.right = right


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
        
            
            
    def exists(self, item):
        if self.top == None:
            return False
        else:
            temp = self.top
            while temp != None:
                if item < temp.value:
                    if temp.left == None:
                        return False
                    else:
                        temp = temp.left
                elif item > temp.value:
                    if temp.right == None:
                        return False
                    else:
                        temp = temp.right

                elif temp.value == item:
                    return True

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
    
