import time
import math

def factorsum(num):
    divsum = 0
    div = 1
    check = math.sqrt(num)
    while div <= check:
        if num % div == 0:
            divsum += div
            divsum += int(num/div)
        div += 1
    if check == int(check):
        divsum -= check
        
    return int(divsum - num)

def abundantfind():
    lista = []

    for number in range(1, 28123):
        
        if factorsum(number) > number:
            lista.append(number)
                    
                    
        
    return lista
start = time.clock()
numlist = abundantfind()
numlist.sort()

numset = list(set(numlist))



sums = list(set([x + y for x in numset for y in numset]))
sums.sort()
sumlist = []
for element in sums:
    if element < 28123:
        sumlist.append(element)


slutsumma = 0
for element in range(28123):
    if element not in sumlist:
        slutsumma += element

print(slutsumma)
print(time.clock() - start)
