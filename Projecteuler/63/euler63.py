import math
import time
import copy

num = 0

for n in range(1, 300):
   for x in range(1, 300):
      if int(math.log(n**x, 10)+1.0001) == x:
         print(n, x, n**x, math.log(n**x, 10))
         num += 1

print(num)
