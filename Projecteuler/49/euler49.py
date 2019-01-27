import math
import time
import copy


def findprimes():
   lista = [2]
   for a in range(2, 10000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(a)
            break
   return lista


start = time.clock()
primelist = findprimes()
print('primes found')

for prime in primelist:
   for jump in range(100, 4000):
      if sorted(str(prime)) == sorted(str(prime+jump)) and sorted(str(prime)) == sorted(str(prime+2*jump)):
         if prime in primelist and prime+jump in primelist and prime+2*jump in primelist:
            print(prime, prime+jump, prime+2*jump)



