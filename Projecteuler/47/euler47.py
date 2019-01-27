import math
import time


def findprimes():
   lista = [2]
   for a in range(2, 1000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(a)
            break
   return lista

def factorize(primes):
   lenlist = []
   
   for number in range(2, 150000):
      faclist = []
      temp = number
      check = 0
      while primes[check] <= math.sqrt(temp):
         
         if temp % primes[check] == 0:
            
            faclist.append(primes[check]) 
            temp //= primes[check]
            if temp / primes[check] in primes:
               faclist.append(temp / primes[check])
               temp /= (temp / primes[check])
         else:
            check += 1

      if faclist == []:
         faclist.append(number)
      elif temp in primes:
         faclist.append(temp)
      
      lenlist.append(len(list(set(faclist))))
   return lenlist


start = time.clock()
primelist = findprimes()
print('primes found')
print(time.clock() - start)

lengths = factorize(primelist)

print('factors found')

check = 4

for element in range(len(lengths)):
   
   if lengths[element:element+check] == check*[check]:
      
      print(element+2)

print(time.clock() - start)
