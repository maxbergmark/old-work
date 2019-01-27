import time
import copy

def permutations(lista, element, string, permlist):
    
    
    if element in range(10):

        if lista[element].isdigit():
            string += lista[element]
            templista = copy.deepcopy(lista)
            templista[element] = 'a'
            if len(string) == 10:
                permlist.append(string)

            for iter1 in range(1, 10):
                eletemp = element + iter1
                permutations(templista, eletemp%10, string, permlist)
    
start = time.clock()
lista = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
permlist = []
for element in range(10):
    print(element, lista, len(permlist), time.clock() - start)
    permutations(lista, element, '', permlist)

permlist.sort()
print(permlist[999999])
