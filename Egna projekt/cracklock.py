import string
import time

def makeNums(string, length):
    lista = []
    if length == 0:
        return ['']
    for i in range(len(string)):
        lista.extend([string[i]+digit for digit in makeNums(string, length-1)])
    return lista

def checkRot(string, lista):
    for i in range(1, len(string)):
        
        if (string[i:]+string[:i] in lista) is True or string[i:]+string[:i] == string:
            
            return lista
    lista.append(string)
    
    return lista

n = 4
lista = []
for i in range(1, n+1):
    if n%i == 0:
        lista.extend(makeNums(string.digits[1:7], i))


print('Generated all sequences')
#print(lista)
lista2 = []
count = 0
perc = 1
print('Checking all rotations')
for element in lista:
    lista2 = checkRot(element, lista2)
    count += 1
    if 100*count / len(lista) > perc:
        print(str(perc)+'% done')
        perc += 1

print('Checked all rotations')
print()

lista2.sort()
#print(lista2)
string = ''.join(lista2)
print(string, string[:n-1])
print(len(string))
