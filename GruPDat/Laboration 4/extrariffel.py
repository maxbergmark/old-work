import time

def riffel(lista):
    result = []
    for test in range(0, int(len(lista)/2)):
        result.append(lista[test])
        result.append(lista[int(test+len(lista)/2)])
        
    return result

lista = []
antal = int(input("Antal kort: "))
for temp in range(0, antal):
    lista.append(temp)

rifflad = lista
rifflad = riffel(rifflad)

count = 1
while rifflad != lista:
    count += 1
    rifflad = riffel(rifflad)

print(count)
                      
