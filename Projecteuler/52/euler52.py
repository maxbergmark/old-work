import math
import time
import copy



start = time.clock()

number = 1
while 1:
   for jump in range(1, 7):
      if sorted(str(number)) != sorted(str(jump*number)):
         number += 1
         break
      elif jump == 6:
         print(number)
         print(time.clock() - start)
         quit()



