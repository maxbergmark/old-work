import math

def toradians(a):

    return a*math.pi / 180

def matrixmult(matrix1, matrix2):

    if len(matrix1) == len(matrix2[0]):    
        matrixprod = []
        
    
        for column in range(0, len(matrix2)):

            for row in range(0, len(matrix1[0])):
                temp = 0
                for element in range(0, len(matrix1)):
                    
                    temp += matrix1[element][row] * matrix2[column][element]
    
    
                matrixprod.append([temp])

        return matrixprod
    else:
        print("Multiplication not possible")



def transform(vector, x, y, a):

    trans = [[x*math.cos(a), x*math.sin(a)], [-y*math.sin(a), y*math.cos(a)]]
   
    return matrixmult(trans, vector)
    


x = float(input("x: "))
y = float(input("y: "))
vector = [[x, y]]

angle = float(input("Angle: "))
xscale = float(input("x-scale: "))
yscale = float(input("y-scale: "))

transform = transform(vector, xscale, yscale, toradians(angle))
print(transform)

