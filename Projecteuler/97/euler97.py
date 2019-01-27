import math
import time
import copy

start = time.clock()
tal = 1
lista = []
for x in range(1, 7830458):
   tal *= 2
   tal = tal % 10**10
   
print((28433*tal+1) % 10**10)
print(time.clock() - start)


