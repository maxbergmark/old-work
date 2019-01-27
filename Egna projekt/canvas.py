from tkinter import *
import math
import time


class Line:

    def __init__(self, line):
        self.line = line

    def update(self):
        self.frame.update_idletasks()


class App:

    def __init__(self, master):
        self.frame = Canvas(master, width = 500, height = 500)
        master.bind('<Key>', self.move)
        self.ground = [450 for i in range(500)]

        self.x = 10
        self.y = 450
        self.jumping = False

        self.frame.pack()
        self.circle = self.frame.create_oval(self.x, self.y, self.x+20, self.y+20, fill = 'black')
        self.line1 = self.frame.create_line(0, 470, 500, 470)
        for i in range(100):
            self.ground[i+300] = 380
        self.line2 = self.frame.create_line(300, 400, 400, 400)

        self.animate()
        

    def animate(self):
        print(self.y, self.x, self.ground[int(self.x)])
        if self.y < self.ground[int(self.x)] and not self.jumping:
            print('fdsafdsa')
            self.jump(0)
        self.frame.after(10, self.animate)

    def jump(self, v0):
        
        self.y -= v0*.1

        self.frame.coords(self.circle, self.x, self.y, self.x+20, self.y+20)

        if self.y <= self.ground[int(self.x+10)]:
            self.frame.after(10, self.jump, v0-10*.1)
        else:
            self.jumping = False
            self.y = self.ground[int(self.x+10)]
            self.frame.coords(self.circle, self.x, self.y, self.x+20, self.y+20)

    def move(self, event):
        if event.keysym == 'Right':
            self.x += 20
        elif event.keysym == 'Left':
            self.x -= 20
        #elif event.keysym == 'Down':
        #    self.y += 20
        elif event.keysym == 'Up':
            #self.y -= 20
            if self.y <= self.ground[int(self.x)]:
                if not self.jumping:
                    self.jumping = True
                    self.jump(50)
            
           
        self.frame.coords(self.circle, self.x, self.y, self.x+20, self.y+20)
        
root = Tk()

app = App(root)


root.mainloop()
