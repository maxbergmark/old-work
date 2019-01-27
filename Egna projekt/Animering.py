import math
import time

def toradians(a):

    return a*math.pi / 180

def matrixmult(matrix1, matrix2):

    if len(matrix1[0]) == len(matrix2):    
        matrixprod = [[0 for i in range(len(matrix2))] for j in range(len(matrix1))]
        
    
        for column in range(0, len(matrix2)):

            for row in range(0, len(matrix1[0])):
                temp = 0
                for element in range(0, len(matrix1)):
                    
                    temp += matrix1[element][row] * matrix2[column][element]
    
    
                matrixprod[column][row] = temp

        return matrixprod
    else:
        print("Multiplication not possible")



def transform(vector, x, y, a):
    tempvector = vector
    trans = [[x*math.cos(a), x*math.sin(a)], [-y*math.sin(a), y*math.cos(a)]]
   
    return matrixmult(trans, tempvector)
    

def animation(vector, x, y, a, frames):
    printer = [[]]
    for row in range(0, 87):
        temp = []
        for column in range(0, 108):
            temp.append(0)

        printer.append(temp)
        
    tempanimation = vector
    for num in range(0, frames+2):
         
        for row in range(0, 87):

            for column in range(0, 108):
                tempanimation = transform(vector, 1+(x-1)*num/frames, 1+(y-1)*num/frames, a*num/frames)
                
                printer[round(tempanimation[0][0])][round(tempanimation[1][0])] = 1
                

        for temp in printer:
            for temp2 in printer[0]:
                if printer[temp][temp2] == 1:
                    print(printer)
        

x = float(input("x: "))
y = float(input("y: "))
vector = [[x, y]]

angle = float(input("Angle: "))
xscale = float(input("x-scale: "))
yscale = float(input("y-scale: "))
frames = int(input("frames: "))
print()
print(vector)
print()
transformer = transform(vector, xscale, yscale, toradians(angle))
print("trans: ", transformer)
print()

animation(vector, xscale, yscale, toradians(angle), frames)
