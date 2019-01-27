import time
import string


fil = open('ordlista.txt')



string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    ordlista.append(element)
print('ord: ', len(ordlista))



svar = []
t = time.clock()
ordlista = set(lista)
print()

count = 0
temp = 0
tstop = 0
maxlen = 0
maxword = []
found = []

t0 = time.clock()

for element in ordlista:
    if element != []:
        if element[1:] in ordlista:
            found.append(element)
            #print(element)



    
found.sort(key=len,reverse=True)
#for element in found:
#    print(element)

print(time.clock()-t0)
print(len(found))