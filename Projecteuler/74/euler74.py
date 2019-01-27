import math
import time
import copy

faclist = [1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880]

def digitfac(n):
   
   if n == 0:
      return 0
   else:
      return faclist[n % 10] + digitfac(n // 10)

def numcheck(n, checklist = None, count = 0):

   if checklist == None:
      checklist = [n]

   temp = digitfac(checklist[-1])
   
   if temp in checklist:
      count += 1
      return count
   else:
      count += 1
      checklist.append(temp)
      return numcheck(n, checklist, count)
      
      

start = time.clock()

count = 0


for x in range(1, 1000001):

   if x % 10000 == 0:
      print(x//10000)
      print((time.clock() - start)*(1000000 - x) / x)
   if numcheck(x) == 60:
      
      count += 1

   

print(count)
print(time.clock() - start)
