import time
import random
from tkinter import *
import functools

class App:
    def __init__(self, master):

        frame = Frame(master, height = 10000, width = 5000)
        frame.pack_propagate(0)
        frame.grid()

        self.grid = [[Button(frame, width = 2, height = 1, relief = 'sunken', command=functools.partial(self.change, i, j)) for i in range(gz)] for j in range(gz)]
        self.grid.append([Button(frame, width = 2, height = 1, text = 'E', command=functools.partial(self.evolve))])
        self.grid[gz].append(Button(frame, width = 2, height = 1, text = 'C', command=functools.partial(self.erase)))

        [[self.grid[i][j].grid(row = i, column = j) for i in range(gz)] for j in range(gz)]
        [self.grid[gz][i].grid(row = gz, column = i) for i in range(2)]

        
    def change(self, column, row):
        global matrix
        if self.grid[row][column]['relief'] == 'sunken':
            matrix[row+1][column+1] = 1
            self.grid[row][column]['relief'] = 'raised'
            self.grid[row][column]['text'] = 'X'
        else:
            self.grid[row][column]['relief'] = 'sunken'
            self.grid[row][column]['text'] = ''
            matrix[row+1][column+1] = 0
        

    def evolve(self):
        global matrix
        global ematrix
        
        for i in range(gz):
            for j in range(gz):
                
                if matrix[i+1][j+1] == 1:
                    if self.checkfriends(i, j) < 2:
                        ematrix[i+1][j+1] = 0
                    elif self.checkfriends(i, j) < 4:
                        ematrix[i+1][j+1] = 1
                    else:
                        ematrix[i+1][j+1] = 0
                elif self.checkfriends(i, j) == 3:
                    ematrix[i+1][j+1] = 1
        #print(matrix)
        #print(ematrix)

        matrix = ematrix
        ematrix = [[0 for i in range(gz+2)]for j in range(gz+2)]
        for i in range(gz):
            for j in range(gz):
                if matrix[i+1][j+1] == 0:
                    self.grid[i][j]['relief'] = 'sunken'
                    self.grid[i][j]['text'] = ''
                else:
                    self.grid[i][j]['relief'] = 'raised'
                    self.grid[i][j]['text'] = 'X'
                        

    def checkfriends(self, row, column):
        #print('check', row, column)
        global matrix
        summa = 0
        for i in range(-1, 2):
            for j in range(-1, 2):
                #print(i, j, matrix[row+i+1][column+j+1])
                if not(i == 0 and j == 0):
                    summa += matrix[row+i+1][column+j+1]
                    
        return summa

    def erase(self):
        global matrix
        global ematrix
        matrix = [[0 for i in range(gz+2)] for j in range(gz+2)]
        ematrix = [[0 for i in range(gz+2)] for j in range(gz+2)]
        for i in range(gz):
            for j in range(gz):
                if matrix[i+1][j+1] == 0:
                    self.grid[i][j]['relief'] = 'sunken'
                    self.grid[i][j]['text'] = ''
                else:
                    self.grid[i][j]['relief'] = 'raised'
                    self.grid[i][j]['text'] = 'X'





root = Tk()
root.title('Game of Life')

global gz
gz = 30

global matrix
matrix = [[0 for i in range(gz+2)] for j in range(gz+2)]
global ematrix
ematrix = [[0 for i in range(gz+2)] for j in range(gz+2)]


app = App(root)

root.mainloop()
