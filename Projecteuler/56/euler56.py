import math
import time
import copy

def numsum(num):
   summa = 0
   while num > 0:
      summa += num % 10
      num //= 10
   return summa

maxsum = 0

for a in range(100):
   for b in range(100):
      if numsum(a**b) > maxsum:
         
         maxsum = numsum(a**b)

print(maxsum)
