import math
import time
import copy


def findprimes():
   lista = [2]
   for a in range(2, 1000000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(a)
            break
   return lista


start = time.clock()
primelist = findprimes()
primerev = copy.copy(primelist)
primerev.reverse()

print('primes found')
print(time.clock() - start)
maxl = 0
maxprime = 0
print(sum(primelist[:850]))
lengths = list(range(850, 2, -1))
#primelist.reverse()
#lengths.reverse()
maxprime = 0
maxlength = 0
for length in lengths:
   print(length)

   for check in primerev:
      
         for num in range(len(primelist)-length+1):

            if check < sum(primelist[num:num+length]):
               break
            
            elif check == sum(primelist[num:num+length]):
               print(check, length, num)
               print(time.clock() - start)
               quit()



