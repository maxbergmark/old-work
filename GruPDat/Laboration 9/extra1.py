from queue import Queue
from word import Word
from time import clock

global ordlista
ordlista = set(open('word3.txt').read().split())



def find(word):
    returnlist = set([])
    for element in ordlista:
        count = 0
        temp = word.child()
        
        for letter in range(3):
            if temp[letter] == element[letter]:
                count += 1
        if count == 2:
            returnlist.add(Word(element, word))
    return returnlist

def wide(word1, found):


    q = Queue()
    word = Word(word1)
    
    q.put(word)

    while not q.empty():
        if q.empty():
            return Word(word1)
        temp = q.get()
        for element in find(word):
            if element.child() not in found:
                found.add(element.child())
                q.put(element)
                
        word = temp


    return temp
    

word1 = str(input('Word 1: '))
maxdist = 0
maxword = ''

t = clock()
answer = wide(word1, set())
temp = answer
count = 0
while temp.fader() != None:
    count += 1
    temp = temp.fader()
maxdist = count
maxword = answer
time = clock()-t




way = []
while True:
    way.append(maxword.child())
    maxword = maxword.fader()
    if maxword.fader() == None:
        way.append(maxword.child())
        break
    
string = ''

for i in range(1, len(way)):
    string += way[-i] + ' -> '
string += way[0]

print(string)
print(maxdist)
print(time)
