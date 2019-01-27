import time
import math

test = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

maxvalue = 0
for a in range(1, 10000):
    string = ''
    for b in range(1, int(10)):
        string += str(a*b)
        if len(string) > 9:
            break
        
            
        
        if sorted(string) == test:
            if maxvalue < int(string):
                maxvalue = int(string)

print(maxvalue)
        


