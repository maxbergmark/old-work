import copy
import time
import winsound
import itertools


inputstr = str(input('letters: '))
t = time.clock()
#inputlist = list(itertools.permutations(inputstr, len(inputstr)))

#stringlist = []
#for element in inputlist:
#    stringlist.append(''.join(list(element)))

#stringlist = list(set(stringlist))

#fil = open(str(len(inputstr)) + 'letters.txt')
fil = open(str(inputstr) + 'letters.txt')
#fil = open('ordlista.txt')


string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    if element != '':
        ordlista.append(element.split()[0])
print('ord: ', len(ordlista))
print()
total = 0
found = []
for element1 in ordlista:
    count = 0
    
    for element2 in ordlista:
        if sorted(element1) == sorted(element2) and element1 != element2 and (element1 not in found):
            if count == 0:
                print('  ' + element1)
                found.append(element1)
            print('  ' + element2)
            found.append(element2)
            count += 1
    total += count
    if count > 0:
        print()

print('hittade ord: ', total)
#svar = []
#t = time.clock()


#print(len(stringlist))
#print()

#count = 0

#for element in stringlist:
#    if element in ordlista:
#        print('  ' + element)
#        count += 1

#print()
#print('hittade ord: ', count)
print('tid: ', round(time.clock() - t, 3), 'seconds')
winsound.Beep(500, 500)
