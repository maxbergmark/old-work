


fil = open("names.txt")
string = fil.read()
print(string)
string = string.strip('"')
print(string)

lista = string.split('","')
lista.sort()

summa = 0

for element in lista:
    tempsum = 0
    for letter in range(len(element)):
        tempsum += (ord(element[letter]) - ord('A') + 1)
    
    #print(element, lista.index(element), (lista.index(element))*tempsum)
    summa += (lista.index(element)+1)*tempsum

print(summa)
