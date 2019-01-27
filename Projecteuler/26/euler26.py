import time
start = time.clock()
nummax = 0
listmax = 0
for number in range(1, 1000):
    tempdiv = 10
    divlist = []
    while tempdiv not in divlist:
        divlist.append(tempdiv)
        tempdiv = 10*(tempdiv % number)
        
    if len(divlist) > listmax:
        listmax = len(divlist)
        nummax = number
    

print(nummax)
print(time.clock() - start)
