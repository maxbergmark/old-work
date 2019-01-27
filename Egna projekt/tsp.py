import random
import string
import time

def makePerms(string, first = ''):
    lista = []

    if len(string) == 1:
        return [first+string, string+first]

    temp = makePerms(string[1:], string[0])

    if first != '':
        for element in temp:
            for i in range(len(element)+1):
                lista.append(element[:i]+first+element[i:])
    else:
        lista = temp


    return lista

def makePoints(n):
    lista = []
    for i in range(n):
        lista.append((random.random(), random.random()))

    return lista

def calcDist(lista):
    dist = [[0 for i in range(len(lista))] for j in range(len(lista))]
    for i in range(len(lista)):
        for j in range(len(lista)):
            dist[i][j] = ((lista[j][0]-lista[i][0])**2+(lista[j][1]-lista[i][1])**2)**0.5
            
    return dist

def calcWay(dists, ways, num):
    mindist = 1000
    minway = ''

    for way in ways:
        dist = 0
        for point in range(num-1):
            dist += dists[int(way[point])][int(way[point+1])]
            if dist > mindist:
                break
        if dist < mindist:
            mindist = dist
            minway = way

    return (mindist, minway)

num = 10

#points = makePoints(num)
points = [(2, 7), (3, 3), (6, 9), (9, 1), (5, 0), (6, 6), (10, 7), (8, 4), (1, 5), (1, 1)]
num = len(points)
disttime = time.clock()
dists = calcDist(points)
disttime = time.clock() - disttime

waystime = time.clock()
ways = makePerms(string.digits[:num])
waystime = time.clock() - waystime

mintime = time.clock()
mindist = calcWay(dists, ways, num)
mintime = time.clock() - mintime

print()
print(mindist)
print(disttime, waystime, mintime)
