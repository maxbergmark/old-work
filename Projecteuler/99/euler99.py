import math
import time
import copy


start = time.clock()
fil = open('base_exp.txt')
string = fil.read()
lista = string.split()

parlista = []
for element in lista:
   temp = element.split(',')
   parlista.append(temp)

maxvalue = 0
maxindex = 0
for element in parlista:
   if int(element[1])*math.log(int(element[0])) > maxvalue:
      maxvalue = int(element[1])*math.log(int(element[0]))
      maxindex = parlista.index(element)

print(maxindex+1)
print(time.clock() - start)
