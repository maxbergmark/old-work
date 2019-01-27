import random
import string
import sys
import time

global fibolist
fibolist = []

def fibo(n):
    
    if n < 0:
        return False
    elif n == 0:
        if fibolist == []:
            fibolist.append(1)
        return 1
    elif n == 1:
        if len(fibolist) == 1:
            fibolist.append(1)
        return 1
    elif n == len(fibolist):
        fibolist.append(fibolist[n-2]+fibolist[n-1])
        return fibolist[n]
    else:
        for i in range(len(fibolist), n+1):      
            return fibo(n-2)+fibo(n-1)

#iters = 1000

#for day in range(1001):
#    daysum = 0
#    for count in range(iters):

while 1:
    day = int(input('Day: '))
    t = time.clock()
    print(fibo(day), time.clock()-t)
        #print(len(daylist), day+1, muk(day), daylist)
        #print(muk(day), time.clock()-t)
        #daysum += time.clock()-t
    #print(day, daysum/iters)
        
