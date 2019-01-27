import random
import string
import sys
import time

global daylist
daylist = []

def muk(day):
    global daylist
    if day < 0:
        return False
    elif day == 0:
        if daylist == []:
            daylist.append(8)
        return daylist[0]
    elif day == 1:
        if len(daylist) == 1:
            daylist.append(16)
        return daylist[1]
    elif day == len(daylist):
        daylist.append(daylist[0] + int((daylist[-2]+daylist[-1])/2))
        return daylist[day]
    elif day < len(daylist):
        return daylist[day]
    
    else:
        for i in range(len(daylist), day+1):
            muk(i)
    return daylist[day]

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
        
