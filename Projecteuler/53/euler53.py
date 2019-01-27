import math
import time
import copy

def fac(n):
   if n == 0:
      return 1
   else:
      return n*fac(n-1)

start = time.clock()

num = 0
for n in range(2, 101):
   for r in range(1, n):
      if fac(n) / (fac(r)*fac(n-r)) > 1000000:
         num += 1

print(num)

