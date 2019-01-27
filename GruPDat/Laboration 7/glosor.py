from mynode import Node, Queue
from word import WordPair
#from myqueue import Queue
import time

myq = Queue()
fil = open('hawaiianW.txt')
string = fil.read()
lista = string.split('\n')
lista = lista[:4]


start = time.clock()

for element in range(0, len(lista)-1, 2):

    myq.put(WordPair(lista[element], lista[element+1]))


antalfel = 0

while not myq.isempty():

    glospar = myq.get()

    print(glospar.ret(0))
    test = str(input('Gissa: '))
    #test = glospar.ret(1)
    if test == glospar.ret(1):
        print('Ratt!')
        glospar.addpoint()
        if glospar.points() < 2:
            myq.put(glospar)
    else:
        antalfel += 1
        print('Fel!')
        myq.put(glospar)



print()
print('Antal fel: ', antalfel)
print('Tid: ', time.clock() - start, 'sekunder')
