from queue import Queue
from word import Word
from time import clock

global ordlista
ordlista = set(open('word3.txt').read().split())


def generatedict():
    global childdict
    childdict = {}
    for element1 in ordlista:
        tempset = set()
        for element2 in ordlista:
            count = 0
            for i in range(3):
                if element1[i] == element2[i]:
                    count += 1
            if count == 2:
                tempset.add(element2)
        childdict[element1] = tempset


def find(word):
    global childdict
    return childdict[word]
                


def wide(word1, tfound):



    q = Queue()
    word = Word(word1)
    
    q.put(word)

    while not q.empty():
        if q.empty():
            return Word(word1)
        temp = q.get()
        for element in find(word.child()):
            if element not in tfound:
                tfound.add(element)
                q.put(Word(element, word))
                
        word = temp


    return temp
    


maxdist = 0
maxword = ''

print('Generating dictionary...')
generatedict()
print('Done!')

print('Finding longest path...')
t = clock()
for word1 in ordlista:

    answer = wide(word1, set())
    temp = answer
   
    count = 0
    while temp.fader() != None:

        count += 1
        temp = temp.fader()
    #print(count, word1)
    if maxdist < count:
        maxdist = count
        maxword = answer
time = clock()-t
print('Done!')
print()


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
