from tkinter import *
import winsound
import string
import random
import functools
import time
import tkinter.messagebox
import sys


#def left(event):
#    root.title('clicked left')
#    info = event.widget.grid_info()   
#    app.button[int(info['row'])][int(info['column'])].config(fg='blue')

def right(event):
    info = event.widget.grid_info()
    global mcount
    global mines
    i = int(info['row'])
    j = int(info['column'])
    if app.button[i][j]['text'] == ' ':
        app.button[i][j].config(fg='red')
        app.button[i][j].config(text='M')
        mcount += 1
    elif app.button[i][j]['text'] == 'M':
        app.button[i][j].config(text=' ')
        mcount -= 1
    app.button[i][j].update_idletasks()
    if mcount == mines:
        mcheck = False
        for i in range(gridsize+1):
            for j in range(gridsize+1):
                if matrix[i][j] == 'x' and app.button[i][j]['text'] != 'M':
                    mcheck = True
                    break
            if mcheck:
                break
        if not mcheck:
            app.win()
   


class App:

    def __init__(self, master):

        frame = Frame(master)
        frame.grid()
        self.root = Tk(useTk = 0)
         
        
        global gridsize
        global mcount
        mcount = 0


        letters = sorted(string.ascii_uppercase + string.ascii_lowercase)
        global matrix
        matrix = [['0' for x in range(gridsize+2)] for x in range(gridsize+2)]
        
        count = 0
        global mines
        mines = min(mines, gridsize*gridsize)


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
        #for i in range(gridsize+2):
            #print(matrix[i])
        
        self.button = [[] for x in range(gridsize+1)]
        global points
        points = 0
        for i in range(gridsize+1):
            for j in range(gridsize+1):
                
                
                if i == 0 and j == 0:
                    global t
                    self.button[i].append((Label(frame, width = 3, text = str(int(time.clock() - t)))))
                elif i == 0:
                    self.button[i].append(Label(frame, text=letters[j-1]))
                elif j == 0:
                    self.button[i].append(Label(frame, text=str(i)))
                else:
                    self.button[i].append(Button(frame, width = 2, height = 1, text=' ', command=functools.partial(self.checkmine, i, j)))
                    #self.button[i][j].bind('<1>', left)
                    self.button[i][j].bind('<3>', right)

                self.button[i][j].grid(row = i, column = j)
        self.updatetime()
  
    def checkmine(self, row, column):
        

        global orow
        global ocol
        orow = row
        ocol = column
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
            self.button[row][column]['relief'] = 'sunken'
            self.button[row][column].update_idletasks()
            
            global points
            global mines
            points += 1
            
            if matrix[row][column] == '0' and row > 0 and row < len(self.button) and column > 0 and column < len(self.button):
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        if row+x >= 0 and row+x < len(self.button) and column+y >= 0 and column+y < len(self.button):
                            if not(x == 0 and y == 0) and not self.button[row+x][column+y]['text'].isdigit() and row + x != 0 and column + y != 0 and row + x != len(matrix) and column + y != len(matrix):
                                self.reveal(row+x, column+y)
        global orow
        global ocol
        
        if points == (len(self.button)-1)**2-mines and row == orow and column == ocol:
            
            
            self.win()
    def updatetime(self):
        self.button[0][0].config(text=str(int(time.clock()-t)))
        self.button[0][0].update_idletasks()
        self.root.after(500, self.updatetime)

        
    def lose(self):
        #wave.open('fail.wav')
        print('  LOSE')
        #winsound.Beep(440, 500)
        #winsound.Beep(523, 500)
        #winsound.Beep(659, 500)
        
        for i in range(gridsize+1):
            for j in range(gridsize+1):
                if matrix[i][j] == 'x':
                    self.button[i][j]['text'] = 'X'
                    self.button[i][j]['fg'] = 'red'
                    self.button[i][j]['relief'] = 'groove'
                    self.button[i][j].update_idletasks()
        winsound.PlaySound('fail.wav', winsound.SND_ALIAS)
        quit('')
        
    def win(self):
        root.title('  WIN')
        global score
        score = time.clock() - t
        print('WIN', round(score, 3), 'seconds')

        winsound.PlaySound('win.wav', winsound.SND_ALIAS)

        
        root.destroy()
        #self.root.destroy()
        


global mines
global gridsize

diff = ''
difflist = ['easy', 'normal', 'hard', 'custom']

print()

while diff not in difflist:
    diff = str(input('  Difficulty? (easy/normal/hard/custom): '))
    if diff not in difflist:
        print('  Could not interpret. Try again')

if diff != 'custom':
    high = open(diff + 'score.txt', 'r')

if diff == 'easy':
    mines = 10
    gridsize = 9

elif diff == 'normal':
    mines = 40
    gridsize = 16

elif diff == 'hard':
    mines = 99
    gridsize = 22

elif diff == 'custom':
    print()
    gridsize = 0
    while gridsize <= 0:
        gridsize = int(input('  Gridsize: '))
        if gridsize < 0:
            print('You can not make it that easy.')
    sys.setrecursionlimit(gridsize*gridsize*gridsize)
    mines = 0
    while mines < 1 or mines > gridsize*gridsize:
        mines = int(input('  Number of mines: '))
        if mines < 1:
            print('  There needs to be mines.')
        if mines > gridsize*gridsize:
            print('  There is not enough space for all those mines.')


print()

if diff != 'custom':

    
    templista = high.read().strip('\n').split('\n')

    lista = []
    for element in templista:
        lista.append((element.split()[0], float(element.split()[1])))
        print('  ', element.split()[0], float(element.split()[1]))
    print()

global t
global score
score = 0
t = time.clock()
root = Tk()
root.title('Minesweeper')

app = App(root)



root.mainloop()


if diff != 'custom' and score != 0:

    print(score)
    count = 0
    high = open(diff + 'score.txt', 'w')
    put = True
    for element in lista:
        if count < 10:
            if score < element[1] and put:
                highname = str(input(''))
                #highname = 'asdf'
                high.write(highname + '   ' + str(round(score, 3)) + '\n')
                count += 1
                put = False
            high.write(element[0] + '   ' + str(element[1]) + '\n')
            count += 1
            

    if count < 10 and put:
        #highname = str(input(''))
        highname = 'asdf'
        high.write(highname + '   ' + str(round(score, 3)) + '\n')
        count += 1
        
        
