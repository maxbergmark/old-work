import string
import time

def makeNums(string, length):
    lista = []
    if length == 0:
        return ['']
    for i in range(len(string)):
        lista.extend([string[i]+digit for digit in makeNums(string, length-1)])
    return lista

def makeNums2(string, length):
    if length == 0:
        return ['']
    lista = [string[i]+rest for i in range(len(string)) for rest in makeNums2(string, length-1)]
    return lista
    

    
    
    
string = str(input('Input: '))
length = int(input('Length: '))

string = ''.join(sorted(list(set(string))))
start = time.clock()
lista = makeNums2(string, length)
end = time.clock()-start


print()
print(lista)
print()
print(len(lista))
print(end)

    
