import copy
import time
import winsound

def tupleadd(tup1, tup2):
    return (tup1[0] + tup2[0], tup1[1] + tup2[1])

def rowmax(matrix, check = False):

    if len(matrix) == 1:
        
        return min(matrix[0])
    
    if len(matrix) == len(matrix[0]):
        for element in range(len(matrix)):
            matrix[1][element] += matrix[0][element]
            
        return matrix[1:]
    
                   
    else:
        rows = matrix[-2:]
        temprows = []
        #print(rows)
        
        for column in range(len(rows[0])):
            
            minvalue = 1000000
            
            for a in range(len(rows[0])):
                
                tempsum = sum(rows[0][min(column, a):max(column, a)+1])+rows[1][a]
                #print(column, tempsum, rows[1][a])

                if minvalue > tempsum:
                    minvalue = tempsum
                    #print(minvalue, 'min', column)
                #print(column, minvalue)
            temprows.append(minvalue)
            #print(rows, column)
        retmatrix = matrix[0:-2]
        retmatrix.append(temprows)
        return retmatrix
        

#matrix = [[131, 673, 234, 103, 18],
#          [201, 96, 342, 965, 150],
#          [630, 803, 746, 422, 111],
#          [537, 699, 497, 121, 956],
#          [805, 732, 524, 37, 331]]

start = time.clock()

fil = open('matrix.txt')
string = fil.read()
lista = string.split('\n')
tempmatrix = []
matrix = []
for element in lista:
    tempmatrix.append(element.split(','))

for row in tempmatrix:
    temprow = []
    for column in row:
        temprow.append(int(column))
    matrix.append(temprow)
#print(matrix)
tempm = []
for row in range(len(matrix[0])):
    temp = []
    for column in range(len(matrix)):
        temp.append(matrix[4-column][row])
    tempm.append(temp)

#print(tempm)
matrix = tempm



while type(matrix) == list:
    #print(matrix)
    #print()
    matrix = rowmax(matrix)


print(matrix)
print(time.clock() - start)
