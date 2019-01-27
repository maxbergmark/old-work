
fil = open('ss100.txt')
prefixlist = open('prefixlist6.txt', 'w')

string = fil.read()
ordlista = string.split()
slutlista = []
for element in ordlista:
    if element.isalpha() == True:
        slutlista.append(element[0:min(len(element),6)].lower())

lista = set(slutlista)
print(len(set(slutlista)))

for element in lista:
    prefixlist.write(element + '\n')
