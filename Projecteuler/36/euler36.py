import math
import time

def base2(number):
   bas2 = 0
   for a in range(21):
      bas2 *= 10
      if 2**(20-a) <= number:
         bas2 += 1
         number -= 2**(20-a)

      
   return str(bas2)
summa = 0
for number in range(1, 1000000):
   if str(number) == str(number)[::-1] and base2(number) == base2(number)[::-1]:
      summa += number

print(summa)
