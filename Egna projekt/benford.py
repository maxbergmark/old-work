import random
import string
import sys
import time

lista = [0 for x in range(10)]

for i in range(2, 10000000):
    a = random.randint(1, i)
    lista[int(str(a)[0])] += 1

for i in range(1, 10):
    print(i, 100*lista[i]/sum(lista)) 


    
