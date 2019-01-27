import time

def findtriangles():
    lista = []
    for a in range(20):
        lista.append(int(0.5*a*(a+1)))
    return lista

fil = open("words.txt")
string = fil.read()

string = string.strip('"')


lista = string.split('","')
lista.sort()

wordlist = []
for element in lista:
    tempsum = 0
    for letter in range(len(element)):
        tempsum += (ord(element[letter]) - ord('A') + 1)
    
    
    wordlist.append(tempsum)

trilist = findtriangles()
count = 0

for element in wordlist:
    if element in trilist:
        count += 1

print(count)
    
