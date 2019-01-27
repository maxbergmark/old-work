import time
import math

def prime(a):
    x = True 
    for i in range(2, int(int(a)**0.5 + 1)):
       if int(a)%i == 0:
           x = False
           break

    return x


test = ['1', '2', '3', '4', '5', '6', '7', '8', '9']
pandigs = []
for a in range(2, 10000000):
    if sorted(str(a)) == test[0:len(str(a))]:
        
        pandigs.append(str(a))





print('pancheck')

maxvalue = 0

for element in pandigs:
    if prime(element):
        if maxvalue < int(element):
            maxvalue = int(element)
print(maxvalue)
