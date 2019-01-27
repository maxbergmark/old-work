from win32api import *
from win32gui import *
import win32con
import time
import pythoncom
import win32gui
from PIL import Image
import pyHook

global start
global size
start = False
size = None

def callback(event):
    print('asdf')
    global size
    global start
    size = int(event.Key)
    start = True
    return True



def grab(bbox = None):
    size, data = grabber()
    im = Image.frombytes("RGB", size, data, "raw", "BGR", (size[0]*3 + 3) & -4, -1)
    if bbox:
        im = im.crop(bbox)
    return im

def difcolor(image, size):

    (x, y) = image.size
    print(x, y)
    diff = 500//size
    summa = [0, 0, 0]
    colors = [[None for i in range(0,470,diff)] for j in range(0,470,diff)]
    poss = [[(560+i,250+j) for i in range(0,470,diff)] for j in range(0,470,diff)]

    print(colors)
    
    for i in range(0, size):
        for j in range(0, size):
            colors[i][j] = image.getpixel((560+diff*j, 250+diff*i))


    print(colors)

    colorlist = []
    for row in colors:
        colorlist.extend(row)
    test = False
    for element in colorlist:
        if colorlist.count(element) == 1:
            pos = colorlist.index(element)
            test = True
    if test:
        xpos = pos//size
        ypos = pos % size
        SetCursorPos(poss[xpos][ypos])
        mouse_event(win32con.MOUSEEVENTF_LEFTDOWN,0,0)
        mouse_event(win32con.MOUSEEVENTF_LEFTUP,0,0)


grabber = Image.core.grabscreen

hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()

while True:

    while not start:
        pythoncom.PumpWaitingMessages()
    start = False
    image = grab()
    
    anomaly = difcolor(image, size)


