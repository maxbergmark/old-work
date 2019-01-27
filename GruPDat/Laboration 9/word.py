class Word():
    
    def __init__(self, w, f = None):
        self.word = w
        self.father = f

    def child(self):
        return self.word

    def fader(self):
        return self.father
