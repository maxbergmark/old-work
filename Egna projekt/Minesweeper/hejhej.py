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

def right(matrix, mines, gridsize, event):
    global mcount
    info = event.widget.grid_info()

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
        mcheck = True
        for x in range(gridsize+1):
            for y in range(gridsize+1):
                if matrix[x][y] == 'x' and app.button[x][y]['text'] != 'M':
                    mcheck = False
                    break
            if not mcheck:
                break
        if mcheck:
            app.win()
   


class App:

    def __init__(self, master, gridsize, mines):

        self.frame = Frame(master)
        self.frame.grid()
        self.root = Tk(useTk = 0)
        self.gridsize = gridsize
        self.mines = mines
        self.clock = True
        global mcount
        mcount = 0
        


        self.letters = sorted(string.ascii_uppercase + string.ascii_lowercase)
        self.matrix = [['0' for x in range(gridsize+2)] for x in range(gridsize+2)]
        
        self.createbuttons()
        self.placemines()
        

    def placemines(self):
        count = 0


        while count < self.mines:
            temprow = random.randint(1, self.gridsize)
            tempcol = random.randint(1, self.gridsize)
            if self.matrix[temprow][tempcol] != 'x':
                self.matrix[temprow][tempcol] = 'x'
                count += 1
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        
                        if self.matrix[temprow + x][tempcol + y] != 'x':
                            self.matrix[temprow + x][tempcol + y] = str(int(self.matrix[temprow + x][tempcol + y]) + 1)



    def createbuttons(self):
        
        self.button = [[] for x in range(gridsize+1)]
        self.points = 0
        for i in range(gridsize+1):
            for j in range(gridsize+1):
                
                
                if i == 0 and j == 0:
                    self.time = 0
                    self.button[i].append(Label(self.frame, width = 3, text = '0'))
                elif i == 0:
                    self.button[i].append(Label(self.frame, text=self.letters[j-1]))
                elif j == 0:
                    self.button[i].append(Label(self.frame, text=str(i)))
                else:
                    self.button[i].append(Button(self.frame, width = 2, height = 1, text=' ', command=functools.partial(self.checkmine, i, j)))
                    
                    self.button[i][j].bind('<3>', functools.partial(right, self.matrix, self.mines, self.gridsize))

                self.button[i][j].grid(row = i, column = j)
        self.updatetime()
  
    def checkmine(self, i, j):
        

        orow = i
        ocol = j
        
        if self.matrix[i][j] != 'x' and not self.button[i][j]['text'].isdigit():
            self.reveal(i, j, orow, ocol)
        elif self.matrix[i][j] == 'x':
            self.lose()
        
    def reveal(self, i, j, orow, ocol):
        
        if i != 0 and j != 0 and i != len(self.matrix) and j != len(self.matrix):
            self.button[i][j]['text'] = self.matrix[i][j]
            self.button[i][j]['fg'] = 'green'
            self.button[i][j]['relief'] = 'sunken'
            self.button[i][j].update_idletasks()
            
            self.points += 1
            
            if self.matrix[i][j] == '0' and i > 0 and i < len(self.matrix)-1 and j > 0 and j < len(self.matrix)-1:
                for x in range(-1, 2):
                    for y in range(-1, 2):
                        if i+x > 0 and i+x < len(self.button) and j+y > 0 and j+y < len(self.button):
                            if not(x == 0 and y == 0) and not self.button[i+x][j+y]['text'].isdigit():
                                self.reveal(i+x, j+y, orow, ocol)

        
        if self.points == self.gridsize**2-mines and i == orow and j == ocol:
            
            self.win()
            
    def updatetime(self):
        if self.clock:
            self.button[0][0].config(text=str(int(time.clock()-self.time)))
            self.button[0][0].update_idletasks()    
            self.root.after(100, self.updatetime)

        
    def lose(self):
        print('  LOSE')
        #winsound.Beep(440, 500)
        #winsound.Beep(523, 500)
        #winsound.Beep(659, 500)
        
        for i in range(self.gridsize+1):
            for j in range(self.gridsize+1):
                if self.matrix[i][j] == 'x':
                    self.button[i][j]['text'] = 'X'
                    self.button[i][j]['fg'] = 'red'
                    self.button[i][j]['relief'] = 'groove'
                    self.button[i][j].update_idletasks()
        winsound.PlaySound('fail.wav', winsound.SND_ALIAS)
        quit()
        
    def win(self):
        self.clock = False
        root.title('  WIN')
        global score
        score = time.clock() - self.time
        root.quit()
        
        print('WIN', round(score, 3), 'seconds')

        winsound.PlaySound('win.wav', winsound.SND_ALIAS)

        
        
        #self.root.quit()
        
        #self.root.destroy()
        




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
        if gridsize <= 0:
            print('You can not make it that easy.')
    sys.setrecursionlimit(2*gridsize**2+1)
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
    print(templista)

    lista = []
    for element in templista:
        lista.append((element.split()[0], float(element.split()[1])))
        print('  ', element.split()[0], float(element.split()[1]))
    print(lista)
    print()


global score
score = 0
root = Tk()
root.title('Minesweeper')

app = App(root, gridsize, mines)

root.mainloop()

if diff != 'custom':
    if score < lista[-1][1]:
        name = str(input('Name: '))
        for i in range(len(lista)):
            if score < lista[i][1]:
                finallist = (lista[:i] + [(name, round(score, 3))] + lista[i:])[:10]
                break

    fil = open(diff + 'score.txt', 'w')
    for element in finallist:
        fil.write(str(element[0]) + '   ' + str(element[1]) + '\n')
