import time

global cases
cases = [[8,0,0],
         [3,1,0],
         [0,2,0],
         [3,2,0],
         [0,0,1],
         [3,0,1]]


def returnChange(change):
    n = sum([a*b for a,b in zip(change,[1,5,10])]) - 8
    change = [0,0,0]
    if n >= 10:
        change[2] += 1
        n -= 10
    if n >= 5:
        change[1] += 1
        n -= 5
    while n > 0:
        change[0] += 1
        n -= 1
    return change

def calcCases(C, change, case = None, coins = 0, cans = 0):
    minans = 100
    global cases
    if cans == C:
        print(coins, 'coins')
        return coins
    for c in cases:
        print(change, c, all(change[i] >= c[i] for i in range(3)), 'check')
        if all(change[i] >= c[i] for i in range(3)):
            print(cans, c, change, [a-b+c for a,b,c in zip(change,c,returnChange(c))])
            return calcCases(C, [a-b+c for a,b,c in zip(change,c,returnChange(c))], c, coins+sum(c), cans+1)



print(calcCases(2, [1, 4, 1]))
