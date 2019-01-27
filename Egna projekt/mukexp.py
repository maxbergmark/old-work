import random
import string
import sys
import time



def muk(day):
    
    if day < 0:
        return False
    elif day == 0:
        return 8
    elif day == 1:
        return 16
    else:
        return muk(0) + int((muk(day-2)+muk(day-1))/2)

#iters = 1000

#for day in range(1001):
#    daysum = 0
#    for count in range(iters):

while 1:
    day = int(input('Day: '))
    t = time.clock()
    print(muk(day), time.clock()-t)
        #print(len(daylist), day+1, muk(day), daylist)
        #print(muk(day), time.clock()-t)
        #daysum += time.clock()-t
    #print(day, daysum/iters)
        
