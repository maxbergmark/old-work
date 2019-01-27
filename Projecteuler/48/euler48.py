import math
import time

summa = 0
for x in range(1, 1001):
   summa += x**x

string = str(summa)
print(string[-10:])
