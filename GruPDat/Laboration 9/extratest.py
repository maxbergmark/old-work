from queue import Queue
from word import Word
from time import clock

global ordlista
ordlista = set(open('word3.txt').read().split())

global found
found = set([])
global foundsofar
foundsofar = set([])

def find(word):
    global foundsofar
    returnlist = set([])
    for element in ordlista:
        count = 0
        temp = word.child()
        
        for letter in range(3):
            if temp[letter] == element[letter]:
                count += 1
        if count == 2:
            returnlist.add(element)
    foundsofar = foundsofar | returnlist
    
    return returnlist

def wide(word1, final):

    if word1 == final:
        return Word(word1)
    q = Queue()
    word = Word(word1)
    
    q.put(word)

    while word.child() != final:
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

for final in ordlista:
    found = set([])
    t = clock()
    answer = wide(word1, final)
    temp = answer
    count = 0
    while temp.fader() != None:
        count += 1
        temp = temp.fader()
    if count > maxdist:
        maxdist = count
        maxword = answer


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
