import random
import time

def mergeSort(lista):
    if len(lista) < 2:
        return lista
    pivot = lista.pop()

    lowlist = []
    highlist = []
    for element in lista:
        if element < pivot:
            lowlist.append(element)
        else:
            highlist.append(element)
    return mergeSort(lowlist)+[pivot]+mergeSort(highlist)


lista = list(range(100))
random.shuffle(lista)
#print(lista)

t0 = time.clock()
lista2 = mergeSort(lista)
t1 = time.clock()

print(t1-t0)
print(lista2)
