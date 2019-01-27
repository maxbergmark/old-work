import math
import time

def findprimes():
   lista = [2]
   for a in range(2, 100000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(a)
            break
   return lista

primelist = findprimes()
print('primes found')

squarelist = [x**2 for x in range(1, 100000)]
print('squares found')
complist = []


for x in range(3, 100000, 2):
    if x not in primelist:
        complist.append(x)
print('composites found')

for element in complist:
    test = True

    for prime in primelist:
        if prime < element:
            if math.sqrt(0.5*(element - prime)) % 1 == 0:
                test = False
                break
    if test:
        print(element)
