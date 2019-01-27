from win32api import *
import win32con
import time
import pythoncom
import win32gui
from PIL import Image
import tkinter as t


class App:

    def __init__(self, master):

        self.frame = t.Frame(master)
        self.label = t.Label(self.frame)
        self.root = master
        self.update()

        
    
    def grab(self, bbox = None):
        grabber = Image.core.grabscreen
        size, data = grabber()
        im = Image.frombytes("RGB", size, data, "raw", "BGR", (size[0]*3 + 3) & -4, -1)
        if bbox:
            im = im.crop(bbox)
        return im

    def update(self):
        #t0 = time.clock()
        image = self.grab()
        
        average = self.color(image)
        #print(time.clock()-t0)
        #print(average)
        mycolor = '#%02x%02x%02x' % tuple(average)
        self.root.configure(bg=mycolor)
        self.root.after(10, self.update)


    def color(self,image):

        (x, y) = image.size
        diff = 10
        summa = [0, 0, 0]
        rows = [False for i in range(y//diff)]
        columns = [False for i in range(x//diff)]

        
        for i in range(0, y, diff):


            row = [0, 0, 0]
            for j in range(0, x, diff):
                pixel = image.getpixel((j, i))

                if pixel != (0, 0, 0):
                    rows[i//diff] = True
                    columns[j//diff] = True

                for k in range(3):
                    row[k] += pixel[k]

                
            for k in range(3):
                summa[k] += row[k]
        rowcount = 0
        columncount = 0
        for i in range(x//diff):
            if columns[i] == True:
                columncount += 1
        for i in range(y//diff):
            if rows[i] == True:
                rowcount += 1
        if rowcount*columncount == 0:
            elements = 1
        else:
            elements = rowcount*columncount


        return [int(summa[i]/(elements)) for i in range(3)]



#    while True:
#        image = grab()
#        t0 = time.clock()
#        average = color(image)
#        print(time.clock()-t0)
#        print(average)
#        time.sleep(0.85)

root = t.Tk(useTk = 1)
root.title('Screen Color')

app = App(root)

root.mainloop()
