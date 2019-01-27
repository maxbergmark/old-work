import string
import time

def makePerms(string, first = ''):
    lista = []

    if len(string) == 1:
        if first == '':
            return [string]
        return [first+string, string+first]

    temp = makePerms(string[1:], string[0])

    if first != '':
        for element in temp:
            for i in range(len(element)+1):
                lista.append(element[:i]+first+element[i:])
    else:
        lista = temp


    return lista

def makePerms2(string, first = ''):
    lista = []
    if len(string) == 1:
        if first == '':
            return [string]
        return [first+string, string+first]

    if first != '':
        for element in makePerms2(string[1:], string[0]):
            lista = [element[:i]+first+element[i:] for i in range(len(element)+1)]
    return lista

string = str(input('Input: '))
t0 = time.clock()
lista = makePerms(string)
t1 = time.clock()-t0
print(lista)
print(len(lista))
print(t1)
