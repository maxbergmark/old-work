import math
import time



def findprimes():
   lista = [2]
   for a in range(3, 100000):
      
      for check in lista:
         if a % int(check) == 0:
            break
         elif int(check) > math.sqrt(a):
            lista.append(a)
            break
   return lista

def change1(primes):
   maxscore = 0
   maxprime = 0
   for prime in primes:
      for digit in range(int(math.log10(prime))+1):
         score = 0
         for value in range(10):
            #print(prime, value, digit, score, int(str(prime)[:digit] + str(value)+str(prime)[digit+1:]))
            if int(str(prime)[:digit] + str(value)+str(prime)[digit+1:]) in primes:
               score += 1
            if score == 6:
               return (prime, score)

         if score > maxscore:
            maxscore = score
            maxprime = prime
   return (maxprime, score)

def change2(primes):
   maxscore = 0
   maxprime = 0
   count = 1000
   for prime in primes:
      if prime - count > 0:
         print(prime)
         count += 1000
      for digit1 in range(int(math.log10(prime))):
         for digit2 in range(digit1 +1, int(math.log10(prime))+1):
            score = 0
            for value in range(10):
               #print(prime, value, digit, int(str(prime)[:digit] + str(value)+str(prime)[digit+1:]))
               temp = int(str(prime)[:digit1] + str(value)+str(prime)[digit1+1:digit2]+str(value)+str(prime)[digit2+1:])
               if temp in primes and int(math.log10(temp)) == int(math.log10(prime)):
                  score += 1
               if score == 7:
                  return (prime, score, digit1, digit2)
            if score > maxscore:
               maxscore = score
               maxprime = prime

   return (maxprime, score)

start = time.clock()
primelist = findprimes()


print('primes found')
print(time.clock() - start)

print(change1(primelist))

print(change2(primelist))
