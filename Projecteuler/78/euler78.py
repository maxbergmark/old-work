import math
import time

dyn = {}

def splitCoin(num, maxlen = None):
   if maxlen == None:
      maxlen = num
   if maxlen <= 1 or num <= 1:
      return 1
   splits = 0

   #print(score, num, maxlen)
   #hej = input()
   for first in range(num, 0, -1):
      if first > maxlen:
         continue
      left = num-first
      if (left, first) in dyn:
         splits += dyn[(left, first)]
      else:
         subSplits = splitCoin(left, first)
         dyn[(left, first)] = subSplits
         splits += subSplits
      #print(score, num, maxlen)
      #hej = input()

   return splits

coins = 0
while True:
   coins += 1
   #coins = int (input ("Coins: "))
   #print(splitCoin(coins))
   if coins % 10 == 0:
      print(coins)
   if splitCoin(coins) % 1000000 == 0:
      print(coins)
      break
