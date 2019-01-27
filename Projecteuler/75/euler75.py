import math
import time
import copy


start = time.clock()
num = 0

for length in range(5, 200):
   ways = 0
   temp = False
   for a in range(1, length-1):
      for b in range(1, length-a):
         if (length-a-b)**2 + b**2 == a**2 and (length-a-b) < b:
            
            ways += 1
            if ways > 1:
               temp = True
               break
      if temp:
         break
         
   if ways == 1:
      num += 1

print(num)
print(time.clock() - start)
