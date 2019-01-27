import string
import time

def makeNums(string, length, counts):
    lista = []
    if length == 0:
        return ['']
    for i in range(len(string)):
        temp = counts[:]
        temp[i] += 1
        if temp[i] <= [5, 5, 5][i]:
            for digit in makeNums(string, length-1, temp):
                if len(digit) > 0:
                    if string[i] != digit[0] and string[i] == 'a' or string[i] != 'a':
                        lista.append(string[i]+digit)
                else:
                    lista.append(string[i]+digit)
    return lista

    

    
    
    
string = 'abc'
length = 15
start = time.clock()
lista = makeNums(string, length, [0, 0, 0])
end = time.clock()-start

print()
print(len(lista))
print(len(set(lista)))
print(end)

    
