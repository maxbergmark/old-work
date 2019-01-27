import random
import string
import sys
import time

def evolve(matrix):
    retmatrix = [[0 for j in range(len(matrix[0]))] for i in range(len(matrix))]
    for row in range(len(matrix)):
        for column in range(len(matrix[0])):
            count = 0
            for x in range(-1, 2):
                for y in range(-1, 2):
                    if (x, y) != (0, 0) and row+x >= 0 and row+x < len(matrix) and column+y >= 0 and column+y < len(matrix[0]):
                        count += int(matrix[row+x][column+y])
            retmatrix[row][column] = str((count +0) % 2)
    return retmatrix

def output(matrix, count):
    tempstr = (len(matrix[0])+2)*'0' + '\n'
    for row in matrix:
        tempstr += '0'
        for element in row:
            if element == '1':
                tempstr += '*'
            else:
                tempstr += ' '
        tempstr += '0\n'
    tempstr += (len(matrix[0])+2)*'0' + ' ' + str(count) + '\n'
    print(tempstr)

def sublist(a, b):
    if a == []: return True
    if b == []: return False
    return b[:len(a)] == a or sublist(a, b[1:])

##matrix = [['1', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0'],
##          ['0', '0', '0', '0', '0', '0', '0', '0', '0', '0']]

size = 5

matrix = [['0' for i in range(size)] for j in range(size)]

#matrix[0][0] = '1'

saved = []
wait = ''
repeat = 0


for size in range(1, 50):
    maxrep = 0
    minrep = 100**100
    t = time.clock()

    for row in range(1, 2):
        
        matrix = [['0' for i in range(size)] for j in range(size)]
        
        matrix[row*len(matrix)//2][row*len(matrix)//2] = '1'
        saved = []
        repeat = 0
        count = 0

        while repeat == 0:

            #if count < 10200 and count > 9900:
            #output(matrix, count)
            
            if (matrix in saved) and repeat == 0:
                
                repeat = (len(saved)-saved.index(matrix))
                if repeat < minrep:
                    minrep = repeat
                if repeat > maxrep:
                    maxrep = repeat
            saved.append(matrix)
            #t = time.clock()
            matrix = evolve(matrix)
            #wait = str(input(time.clock()-t))
            #print()
            #time.sleep(0.1)
            count += 1

        #print(size, repeat, row, column)
        matrix[row*len(matrix)//2][row*len(matrix)//2]
        #wait = input()
    print(size, minrep, maxrep, len(saved), round(time.clock()-t, 10))
