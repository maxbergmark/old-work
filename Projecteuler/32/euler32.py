import time
import string

summa = []
test = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

for a in range(1, 3000):
    
    for b in range(1, 3000):
        string = str(a)+str(b)+str(a*b)
        #print(string)
        string = sorted(string)
        #print(string)
        
        if string == test:
            print(a, b, a*b)
            summa.append(a*b)

print(sum(set(summa)))
