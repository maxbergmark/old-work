import time
import sys

def Ackermann(m,n):
    if m == 0:
        return n+1
    if n == 0:
        return Ackermann(m-1,1)
    return Ackermann(m-1, Ackermann(m,n-1))
sys.setrecursionlimit(10000)
print(Ackermann(3,9))
