
fil = open('wordfreq.txt')
write = open('wordfreqlist.txt', 'w')

string = fil.read()
ordlista = string.split('\n')
print(ordlista[:10])

slutlista = []
for element in ordlista:
    print(element.split())
    if element != []:
        if element.split()[0].isalpha() == True:
            slutlista.append(element.lower().split()[0])

lista = list(set(slutlista))
print(len(set(slutlista)))
lista.sort()

for element in lista:
    write.write(element + '\n')
