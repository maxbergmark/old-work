from queue import Queue
from word import Word
from time import clock

global ordlista
ordlista = open('word3.txt').read().split()

global found
found = []

def find(word):
    returnlist = []
    for element in ordlista:
        count = 0
        temp = word.child()
        
        for letter in range(3):
            if temp[letter] == element[letter]:
                count += 1
        if count == 2:
            returnlist.append(Word(element, word))
    
    return returnlist

def wide(word1, final):
    q = Queue()
    word = Word(word1)
    
    q.put_nowait(word)

    while word.child() != final:
        temp = q.get_nowait()
        for element in find(word):
            if element.child() not in found:
                found.append(element.child())
                q.put_nowait(element)
                
        word = temp

    return temp
    

word1 = str(input('Word 1: '))
final = str(input('Word 2: '))

t = clock()

answer = wide(word1, final)

print(clock()-t)


way = []
while True:
    way.append(answer.child())
    answer = answer.fader()
    if answer.fader() == None:
        way.append(answer.child())
        break
    
string = ''

for i in range(1, len(way)):
    string += way[-i] + ' -> '
string += way[0]

print(string)   
