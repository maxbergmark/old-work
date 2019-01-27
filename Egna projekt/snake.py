from tkinter import *
# import winsound
import string
import random
import functools
import time
import tkinter.messagebox
import sys

global t
global tlist
tlist = []

class App:

    def __init__(self, master):

        self.frame = Frame(master)
        self.frame.grid()
        master.bind("<Key>", self.keyPressed)
        self.creategrid()
        self.pos = [[2, 1], [1, 1]]
        self.dir = [1, 0]
        self.seq = 0
        self.count = 0
        self.totalloss = 0
        self.steps = 0
        self.tlist = []

        self.place()
        self.move()
        



    def creategrid(self):
        self.gs = 20
        self.button = [[None for i in range(self.gs)] for j in range(self.gs)]
        for i in range(self.gs):
            for j in range(self.gs):
                self.button[i][j] = Label(self.frame, width = 30, height = 30, bitmap = 'warning', bg = 'black', fg = 'black')
                self.button[i][j].grid(row = i, column = j)

    def move(self):
        
        if self.seq == 0:
            #print('check1')
            if self.tpos[0] == self.pos[0][0] and self.tpos[1] < self.pos[0][1] and self.pos[0][0] != 0 and self.calcloss() < self.gs**2 - len(self.pos) - self.totalloss:
                #print(self.calcloss(), self.gs**2-len(self.pos))
                #print('check6')

                self.totalloss += self.calcloss()
                self.dir = [0, -1]
                self.seq = 3

            elif self.tpos[1] > self.pos[0][1] and self.pos[0][0] != 0 and self.pos[0][1] > 1 and self.calcloss2() < self.gs**2 - len(self.pos) - self.totalloss:

                self.totalloss += self.calcloss2()
                self.dir = [0, -1]
                self.seq = 4
                #print(self.pos[0])
                    

                
                #print('check3')
            elif self.pos[0][0] == 0:
                #print('check7')
                if self.pos[0][1] == self.gs-1:
                    self.dir = [1, 0]
                else:
                    self.dir = [0, 1]

            elif self.pos[0][0] == self.gs-1 and self.pos[0][1] % 2 == 1:
                #print('asdf')
                self.dir = [0, -1]
                self.seq = 2
            elif self.pos[0][0] == 1 and self.pos[0][1] < self.gs-1 and self.pos[0][1] > 0 and self.pos[0][1] % 2 == 0:
                #print('check2')
                self.dir = [0, -1]
                self.seq = 1

            elif self.pos[0][1] % 2 == 0:
                self.dir = [-1, 0]
                #print('check4')
            elif self.pos[0][1] % 2 == 1:
                self.dir = [1, 0]
                #print('check5')



            #elif self.count > 5:
            #    temp = [[0, -1], [0, 1], [-1, 0], [1, 0]]
            #    tempr = [-1*i for i in self.dir]

            #    temp.remove(tempr)

            #    self.dir = random.choice(temp)
            #    self.count = 0
                
        elif self.seq == 1:
            self.dir = [1, 0]
            self.seq = 0
        elif self.seq == 2:
            self.dir = [-1, 0]
            self.seq = 0

        elif self.seq == 4:
            if self.pos[0][1] == 0:
                self.seq = 0
                self.dir = [-1, 0]

        if self.pos[0][1] == 0 and self.pos[0][0] != 0:
            self.dir = [-1, 0]
                
        self.totalloss = max(0, self.totalloss-1)

        #print(self.totalloss, self.seq, self.pos[0], self.dir)
        if self.button[self.pos[0][0] + self.dir[0]][self.pos[0][1] + self.dir[1]]['bg'] == 'green':
            self.pos.insert(0, [self.pos[0][0] + self.dir[0], self.pos[0][1] + self.dir[1]])
            self.totalloss += 1
            self.button[self.pos[0][0]][self.pos[0][1]]['bg'] = 'white'
            self.button[self.pos[0][0]][self.pos[0][1]]['fg'] = 'white'

                
            if self.seq == 3:
                self.seq = 0

            self.place()

            

        
        else:

            for i in range(len(self.pos)-1, 0, -1):
                self.pos[i] = self.pos[i-1][:]

            self.pos[0][0] += self.dir[0]
            self.pos[0][1] += self.dir[1]
            self.button[self.pos[0][0]][self.pos[0][1]]['bg'] = 'white'
            self.button[self.pos[0][0]][self.pos[0][1]]['fg'] = 'white'
            
        self.button[self.pos[-1][0]][self.pos[-1][1]]['bg'] = 'black'
        self.button[self.pos[-1][0]][self.pos[-1][1]]['fg'] = 'black'    

        self.frame.update_idletasks()
        #print(self.totalloss, self.seq, self.pos[0], self.dir)
        tempcount = 0
        for i in range(self.gs):
            for j in range(self.gs):
                tempcount += (self.button[i][j]['bg'] == 'white')
        if len(self.pos)-1 != tempcount:
            print('error')
            time.sleep(.1)
        
        #hej = input()
        self.steps += 1
        self.frame.after(2, self.move)

    def place(self):
        self.tlist.append(self.steps)
        self.count += 1
        if self.count == self.gs**2-1:

            print(self.steps)
            for i in range(len(self.tlist)-1):
                print(self.tlist[i+1]-self.tlist[i])
            quit()
        self.tpos = [random.randint(0, self.gs-1), random.randint(0, self.gs-1)]
        mov = self.pos[-1]
        while self.button[self.tpos[0]][self.tpos[1]]['bg'] == 'white' or self.tpos == mov:

            self.tpos = [random.randint(0, self.gs-1), random.randint(0, self.gs-1)]

        self.button[self.tpos[0]][self.tpos[1]]['bg'] = 'green'
        self.button[self.tpos[0]][self.tpos[1]]['fg'] = 'red'

    def calcloss(self):
        temp = self.pos[0]
        uloss = (temp[1]-self.tpos[1]-(temp[1]-self.tpos[1])%2)*(temp[0]-1)
        dloss = (temp[1]-self.tpos[1]+(temp[1]-self.tpos[1])%2)*(self.gs -temp[0]-1)

        return uloss + dloss + temp[1] - self.tpos[1] + .2*self.gs**2

    def calcloss2(self):
        temp = self.pos[0]
        uloss = (temp[1]-(temp[1])%2)*(temp[0]-1)
        dloss = (temp[1]+(temp[1])%2)*(self.gs -temp[0]-1)

        return uloss + dloss + temp[1] + .2*self.gs**2

    def keyPressed(a, event):

        if (event.keysym == "Up"):
            app.dir = [-1, 0]
        elif (event.keysym == "Down"):
            app.dir = [1, 0]
        elif (event.keysym == "Left"):
            app.dir = [0, -1]
        elif (event.keysym == "Right"):
            app.dir = [0, 1]

            
            

root = Tk()
root.title('Snake')

global t
t = time.clock()
app = App(root)

root.mainloop()

