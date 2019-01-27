from tkinter import *
# import winsound
import string
import random
import functools
import time
import tkinter.messagebox
import sys



class App:

    def __init__(self, master, gs, speed, player):

        self.master = master

        self.frame = Frame(master)
        self.frame.grid()
        master.bind("<Key>", self.keyPressed)

        self.pos = [[1, 1], [0, 1]]
        self.dir = [1, 0]

        self.count = 0
        self.totalloss = 0
        self.steps = 0
        self.tlist = []
        self.bot = True
        self.player = player
        self.speed = speed
        self.gs = gs

        self.creategrid()
        self.place([1, 1])
        self.move()
        



    def creategrid(self):
        self.button = [[None for i in range(2*self.gs-1)] for j in range(2*self.gs-1)]
        for i in range(2*self.gs-1):
            for j in range(2*self.gs-1):
                if i%2 == 0 and j%2 == 0:
                    self.button[i][j] = Label(self.frame, width = 300//self.gs, height = 300//self.gs, bitmap = 'warning', bg = 'black', fg = 'black')
                elif i%2 == 1 and j%2 == 0:
                    self.button[i][j] = Label(self.frame, width = 300//self.gs, height = 50//self.gs, bitmap = 'warning', bg = 'black', fg = 'black')
                elif j%2 == 1 and i%2 == 0:
                    self.button[i][j] = Label(self.frame, width = 50//self.gs, height = 300//self.gs, bitmap = 'warning', bg = 'black', fg = 'black')
                else:
                    self.button[i][j] = Label(self.frame, width = 50//self.gs, height = 50//self.gs, bitmap = 'warning', bg = 'black', fg = 'black')
                self.button[i][j].grid(row = i, column = j)

    def move(self):

        if self.player == False:
            self.calcmove()
        #print(self.totalloss, self.seq, self.pos[0], self.dir)
        temp = [self.pos[0][i] + self.dir[i] for i in range(2)]
        if temp[0] < 0 or temp[0] >= self.gs or temp[1] < 0 or temp[1] >= self.gs:
            print('OUT OF BOUNDS')
            quit()
        
        if self.button[2*self.pos[0][0] + 2*self.dir[0]][2*self.pos[0][1] + 2*self.dir[1]]['bg'] == 'green':
            self.pos.insert(0, [self.pos[0][0] + self.dir[0], self.pos[0][1] + self.dir[1]])
            self.totalloss += 1
            self.drawsnake()
            self.place()


        
        else:

            for i in range(len(self.pos)-1, 0, -1):
                self.pos[i] = self.pos[i-1][:]

            self.pos[0][0] += self.dir[0]
            self.pos[0][1] += self.dir[1]
            self.drawsnake()



        tempcount = 0
        for i in range(self.gs):
            for j in range(self.gs):
                tempcount += (self.button[2*i][2*j]['bg'] == 'white')
        if len(self.pos)-1 != tempcount:
            print('error')

            #self.frame.grid_forget()
            #self.frame.destroy()
            #self.master.destroy()
            
            time.sleep(1)
            #quit()
        
        self.steps += 1
        self.frame.after(self.speed, self.move)

    def drawsnake(self):

            
        self.button[2*self.pos[0][0]][2*self.pos[0][1]]['bg'] = 'white'
        self.button[2*self.pos[0][0]][2*self.pos[0][1]]['fg'] = 'white'
        self.button[2*self.pos[0][0]][2*self.pos[0][1]].update_idletasks()

        self.button[self.pos[0][0]+self.pos[1][0]][self.pos[0][1]+self.pos[1][1]]['bg'] = 'white'
        self.button[self.pos[0][0]+self.pos[1][0]][self.pos[0][1]+self.pos[1][1]]['fg'] = 'white'
        self.button[self.pos[0][0]+self.pos[1][0]][self.pos[0][1]+self.pos[1][1]].update_idletasks()

        self.button[2*self.pos[-1][0]][2*self.pos[-1][1]]['bg'] = 'black'
        self.button[2*self.pos[-1][0]][2*self.pos[-1][1]]['fg'] = 'black'
        self.button[2*self.pos[-1][0]][2*self.pos[-1][1]].update_idletasks()
        self.button[self.pos[-1][0]+self.pos[-2][0]][self.pos[-1][1]+self.pos[-2][1]]['bg'] = 'black'
        self.button[self.pos[-1][0]+self.pos[-2][0]][self.pos[-1][1]+self.pos[-2][1]]['fg'] = 'black'
        self.button[self.pos[-1][0]+self.pos[-2][0]][self.pos[-1][1]+self.pos[-2][1]].update_idletasks()

    def calcmove(self):

        if self.tpos[1] < 2*self.pos[0][1] and self.pos[0][0] != 0 and 2*self.pos[0][0] == self.tpos[0] and self.totheleft() and self.bot and self.gs**2 > 2*len(self.pos): 

            self.dir = [0, -1]

        elif self.pos[0][0] > 0 and self.pos[0][1] > 0 and self.tpos[0] == 0 and self.totheleft() and self.bot and self.gs**2 > 2*len(self.pos):

            self.dir = [0, -1]

        elif self.tpos[1] > 2*self.pos[0][1] and self.pos[0][0] != 0 and self.totheleft() and self.bot and self.gs**2 > 2*len(self.pos):
           
            self.dir = [0, -1]                    


        elif self.pos[0][0] == 0:

            if self.pos[0][1] == self.gs-1:
                self.dir = [1, 0]
            else:
                self.dir = [0, 1]

        elif self.pos[0][0] == self.gs-1 and self.pos[0][1] % 2 == 1:
                
            self.dir = [0, -1]

        elif self.pos[0][0] == 1 and self.pos[0][1] < self.gs-1 and self.pos[0][1] > 0 and self.pos[0][1] % 2 == 0:
                
            self.dir = [0, -1]

        elif self.pos[0][1] % 2 == 0:
                self.dir = [-1, 0]
             
        elif self.pos[0][1] % 2 == 1:
                self.dir = [1, 0]
       
        if self.pos[0][1] == 0 and self.pos[0][0] != 0:
            self.dir = [-1, 0]
                

    def place(self, nallow = None):
        self.tlist.append(self.steps)
        self.count += 1
        if self.count == self.gs**2-1:
            print(self.steps)
            print((self.gs)**4 / 4 / self.steps)

            quit()
            root.destroy()
            #self.frame.grid_forget()
            #self.frame.destroy()
            #self.master.destroy()
            
        self.tpos = [2*random.randint(0, self.gs-1), 2*random.randint(0, self.gs-1)]
        mov = self.pos[-1]
        while self.button[self.tpos[0]][self.tpos[1]]['bg'] == 'white' or self.tpos == mov or self.tpos == nallow:

            self.tpos = [2*random.randint(0, self.gs-1), 2*random.randint(0, self.gs-1)]

        self.button[self.tpos[0]][self.tpos[1]]['bg'] = 'green'
        self.button[self.tpos[0]][self.tpos[1]]['fg'] = 'red'
        self.button[self.tpos[0]][self.tpos[1]].update_idletasks()


    def totheleft(self):
                
        tempcount = 0
        for i in range(1, self.gs):
            if self.button[2*i][2*self.pos[0][1]-2]['bg'] == 'white':
                tempcount += 1
        
        return tempcount == 0


    def keyPressed(a, event):
        
        if (event.keysym == "Up") and app.dir != [1, 0]:
            app.dir = [-1, 0]
        elif (event.keysym == "Down") and app.dir != [-1, 0]:
            app.dir = [1, 0]
        elif (event.keysym == "Left") and app.dir != [0, 1]:
            app.dir = [0, -1]
        elif (event.keysym == "Right") and app.dir != [0, -1]:
            app.dir = [0, 1]


speed = int(input('Speed: '))
gs = int(input('Gridsize: '))
player = str(input('Player(y/n): '))
player = (player == 'y')

root = Tk()
root.title('Snake')

print(gs*gs/4*gs*gs)

app = App(root, gs, speed, player)
# if speed != 0:
    # root.iconify()
    # root.update()
    # root.deiconify()

root.mainloop()
