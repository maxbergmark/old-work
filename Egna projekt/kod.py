import random
import string
import sys
import time

sys.setrecursionlimit(10)

def check(letterlist):
    for letter in letterlist:
        if score == 0 or letter == solver[score]:
            score += 1
        else:
            return check(letterlist[])
    return score

letters = sorted(string.ascii_lowercase)
letterlist = []
for i in range(100):
    letterlist.append(random.choice(letters))

letterlist.extend(['a', 'b', 'r', 'a', 'k', 'a', 'd', 'a', 'b', 'r', 'a'])

for i in range(100):
    letterlist.append(random.choice(letters))

global solver
solver = ['a', 'b', 'r', 'a', 'k', 'a', 'd', 'a', 'b', 'r', 'a']
global nex
nex = [0, 0, 1, 1, 0, 2, 0, 2, 0, 1, 1, 0]
score = 0
i = 0
t = time.clock()
while score != len(solver):
    letter = letterlist[i]
    score = check(letterlist[i:i+len(solver)])
    i += 1
    print(i, score, letterlist[i:i+score])

time1 = time.clock()-t
print(time1)
t = time.clock()
for i in range(len(letterlist)-len(solver)):
    count = 0
    for j in range(len(solver)):
        if letterlist[i+j] == solver[j]:
            count += 1
        else:
            break
    if count == len(solver):
        time2 = time.clock()-t
        break

print(time2)
print(time2/time1)

