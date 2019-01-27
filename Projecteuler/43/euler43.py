import time
import math
import itertools


pantuple = list(itertools.permutations(range(10), 10))

print('tuples found')
#print(pantuple)
panstring = []
for element in pantuple:
    element = str(element)
    panstring.append(element.strip('()').replace(', ', ''))
    
    
primelist = [2, 3, 5, 7, 11, 13, 17]
print('strings made')
summa = 0

for element in panstring:
    
    for letter in range(1, 8):
        if element == '1406357289':
            print(int(element[letter:letter+3]), primelist[letter-1])
        
        if int(element[letter:letter+3]) % primelist[letter-1] != 0:
            break
        elif letter == 7:
            summa += int(element)
        
        
print(summa)
