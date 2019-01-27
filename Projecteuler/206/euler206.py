import math
import time
import copy

def check(n):
   for test in range(8, 0, -1):
      n //= 100
      if n % 10 != test:
         return False
   return True

start = time.clock()

for x in range(10101010, 13890267):
   temp = 10*x+3
   if check(temp*temp):
      x = 100*x + 30
      break
   temp = 10*x+7
   if check(temp*temp):
      x = 100*x + 70
      break
   
print(x)
print(time.clock() - start)
