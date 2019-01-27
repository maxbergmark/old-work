class WordPair():
    def __init__(self, l1 = None, l2 = None):
        self.point = 0
        self.lang1 = l1
        self.lang2 = l2
        
    def addpoint(self):
        self.point += 1
                
    def points(self):
        return self.point

    def ret(self, l):
        if l == 0:
            return self.lang1
        if l == 1:
            return self.lang2


        
            
            
            
