import time
import math



maxvalue = 0
string = ''
for a in range(1, 1000000):
    
    
    string += str(a)
    if len(string) > 1000000:
        
        break
        

d = 1
product = 1
while d <= 1000000:
    product *= int(string[d-1])
    print(string[d])
    d *= 10

print(product)
