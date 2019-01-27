

class Queue:

    def __init__(self):
        self.queue = []
        
    def put(self, x):
        self.queue.append(x)

    def get(self):
        if self.queue != []:
            return self.queue.pop(0)
        else:
            return None
        
    def isempty(self):
        return self.queue == []
    
