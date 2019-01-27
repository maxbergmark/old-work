import math
import time

def findprimes():
   lista = ['2']
   for a in range(2, 1000000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(str(a))
            break
   return lista

def circulate(lista):
   resultlist = []
   for element in lista:
      
      temp = element
      for iters in range(len(element)):
         temp = temp[1:] + temp[0]
         if temp not in lista:
            break
         elif iters == len(element)-1:
            #print(int(element) / 10000)
            resultlist.append(int(element))
   return resultlist


start = time.clock()
primelist = findprimes()




circlist = circulate(primelist)
print(circlist)
print(len(circlist))
print(time.clock() - start)
