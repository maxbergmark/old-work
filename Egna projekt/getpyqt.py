from win32api import *
import win32con
import time
import sys
import pythoncom
import win32gui
from PIL import Image
from PyQt4.QtGui import *





def color(image):
    x = image.width()
    y = image.height()
    #print(w, h)
    diff = 20
    summa = [0, 0, 0]
    rows = [False for i in range(y//diff)]
    columns = [False for i in range(x//diff)]

    
    for i in range(0, y, diff):


        row = [0, 0, 0]
        for j in range(0, x, diff):
            i_colour = image.pixel(j, i)
            pixel = ((i_colour >> 16) & 0xff), ((i_colour >> 8) & 0xff), (i_colour & 0xff)

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

app = QApplication(sys.argv)
while True:
    t0 = time.clock()
    
    
    #t0 = time.clock()
    image = QPixmap.grabWindow(QApplication.desktop().winId())
    image = image.toImage()
    #print(time.clock()-t0)
    #t0 = time.clock()
    average = color(image)
    print(time.clock()-t0)
    print(average)
    
