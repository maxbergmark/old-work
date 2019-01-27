from tkinter import *
import winsound
import string
import random
import functools
import time


def left(event):
    root.title('clicked left')
    self.button[2][2].config(fg='blue')

def right(event):
    root.title('clicked right')
    self.button[2][2].config(fg='red')


class App:

    def __init__(self, master):

        frame = Frame(master)
        frame.grid()
        global gridsize


        letters = sorted(string.ascii_uppercase + string.ascii_lowercase)
        global matrix
        matrix = [['0' for x in range(gridsize+2)] for x in range(gridsize+2)]
        
        count = 0
        global mines

        while count < mines:
            temprow = random.randint(1, gridsize)
            tempcol = random.randint(1, gridsize)
            if matrix[temprow][tempcol] != 'x':
                matrix[temprow][tempcol] = 'x'
                count += 1
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        if not(x == 0 and y == 0):
                            if matrix[temprow + x][tempcol + y] != 'x':
                                matrix[temprow + x][tempcol + y] = str(int(matrix[temprow + x][tempcol + y]) + 1)
        for i in range(gridsize+2):
            print(matrix[i])
        
        self.button = [[] for x in range(gridsize+1)]
        global points
        points = 0
        for i in range(gridsize+1):
            for j in range(gridsize+1):
                
                
                if i == 0 and j == 0:
                    global t
                    self.button[i].append((Label(frame, text = str(int(time.clock() - t)))))
                elif i == 0:
                    self.button[i].append(Label(frame, text=letters[j-1]))
                elif j == 0:
                    self.button[i].append(Label(frame, text=str(i)))
                else:
                    self.button[i].append(Button(frame, width = 3, text='  ', command=functools.partial(self.checkmine, i, j)))

                self.button[i][j].grid(row = i, column = j)
  
    def checkmine(self, row, column):
        self.button[0][0]['text'] = str(int(time.clock() - t))
      
        if matrix[row][column] != 'x' and not self.button[row][column]['text'].isdigit():
            self.reveal(row, column)
        elif matrix[row][column] == 'x':
            self.lose()
        else:
            pass
        
    def reveal(self, row, column):
        
        if row != 0 and column != 0 and row != len(matrix) and column != len(matrix):
            self.button[row][column]['text'] = matrix[row][column]
            self.button[row][column]['fg'] = 'green'
            self.button[row][column]['relief'] = SUNKEN
            global points
            global mines
            points += 1
            if matrix[row][column] == '0' and row > 0 and row < len(self.button) and column > 0 and column < len(self.button):
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        if row+x >= 0 and row+x < len(self.button) and column+y >= 0 and column+y < len(self.button):
                            if not(x == 0 and y == 0) and not self.button[row+x][column+y]['text'].isdigit() and row + x != 0 and column + y != 0 and row + x != len(matrix) and column + y != len(matrix):
                                self.reveal(row+x, column+y)
        if points == (len(matrix)-2)**2-mines:
            print('WIN', time.clock() - t)
            winsound.Beep(440, 500)
            winsound.Beep(554, 500)
            winsound.Beep(659, 500)
            quit()
        
    def lose(self):
        print('lose')
        winsound.Beep(440, 500)
        winsound.Beep(523, 500)
        winsound.Beep(659, 500)
        quit()


global mines
mines = int(input('number of mines: '))
global gridsize
gridsize = int(input('gridsize: '))
global t
t = time.clock()
root = Tk()

app = App(root)



root.mainloop()
