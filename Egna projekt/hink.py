import random
import string
import sys
import time

global hink1
global hink2
global hink3

hink1 = 10
hink2 = 5
hink3 = 3

def check(x):
    global hink1
    global hink2
    global hink3
    ret = False
    
        
    if x > hink1 + hink2 + hink3:
        return False
    for a in range(2):
        for b in range(-(hink1//hink2), (hink1//hink2)+2):
            for c in range(-(hink1//hink3 + hink2//hink3), (hink1//hink3 + hink2//hink3)+2):
                #print(a*hink1 + b*hink2 + c*hink3, a, b, c)
                if a*hink1 + b*hink2 + c*hink3 == x:
                    print('\nVolume:',x, '\nBucket', hink1, a, '\nBucket', hink2, b, '\nBucket', hink3, c, '\n')
                    ret = True
    return ret


#while True:
#count = 0
#for hink1 in range(1, 10):
#    for hink2 in range(1, hink1):
#        for hink3 in range(1, hink2):
#            if check(10):
#                count += 1
#print(count)
while True:
#for x in range(1, hink1+hink2+hink3+1):
    x = int(input('Volym: '))
    #check(x)
    #if check(x):
    #    print(x)
    print(check(x))
    print('\n')
