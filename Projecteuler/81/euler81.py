import time

def rowmax(matrix, check = False):

    if len(matrix) == len(matrix[0]):
        rows = matrix[-2:]
        temprows = []
        for column in range(len(rows[0])):
            
            minvalue = 1000000
            
            for a in range(column, len(rows[0])):
                
                tempsum = sum(rows[0][column:a+1])+sum(rows[1][a:])
                
                if minvalue > tempsum:
                    minvalue = tempsum

            temprows.append(minvalue)
        retmatrix = matrix[0:-2]
        retmatrix.append(temprows)
        return retmatrix

    elif len(matrix) == 2:
        minvalue = 1000000
        for column in range(len(matrix[0])):
            
            if minvalue > sum(matrix[0][0:column+1]) + matrix[1][column]:
                minvalue = sum(matrix[0][0:column+1]) + matrix[1][column]
        return minvalue
    
                   
    else:
        rows = matrix[-2:]
        temprows = []
        
        for column in range(len(rows[0])):
            
            minvalue = 1000000
            
            for a in range(column, len(rows[0])):
                
                tempsum = sum(rows[0][column:a+1])+rows[1][a]

                if minvalue > tempsum:
                    minvalue = tempsum
            temprows.append(minvalue)
        retmatrix = matrix[0:-2]
        retmatrix.append(temprows)
        return retmatrix

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

start = time.clock()
while type(matrix) == list:
    matrix = rowmax(matrix)


print(matrix)
print(time.clock() - start)
