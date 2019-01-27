import time
import random

def bogoSort(lista):
    count = 0
    while sorted(lista) != lista:
        random.shuffle(lista)
        
        count += 1
    return (lista, count)

tal = int(input('Längd: '))
lista = list(range(tal))
random.shuffle(lista)
print(lista)
#t0 = time.clock()
counter = 0
rep = 1000
for i in range(rep):
    random.shuffle(lista)
    sort = bogoSort(lista)
    counter += sort[1]
    print(counter)

#t1 = time.clock()

print(sort[0])
print(counter//rep)
#print(t1-t0)
