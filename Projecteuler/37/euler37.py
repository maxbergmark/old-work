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

def trunctate(lista):
   summa = 0
   count = 0
   for element in lista:
      
      lefttemp = element
      righttemp = element
      for iters in range(len(element)):
         lefttemp = lefttemp[1:]
         righttemp = righttemp[:len(righttemp)-1]
         #print(lefttemp, righttemp, element)
         if lefttemp not in lista or righttemp not in lista:
            
            break
         elif iters == len(element)-2:
            #print(int(element) / 10000)
            print(element)
            summa += int(element)
            count += 1
   return (summa, count)


start = time.clock()
primelist = findprimes()




summa = trunctate(primelist)
print(summa)
print(time.clock() - start)
