from win32api import *
from win32gui import *
import win32con
import time
import pythoncom
import win32gui
import win32com.client as comclt
from PIL import Image
import pyHook

global start
global size
start = False
size = None

def callback(event):
    global start
    global size
    start = True
    size = GetCursorPos()
    return True

def checkCol(target, color):
    #target = (141, 35, 13)
    diff = 0
    for i in range(3):
        diff += abs(target[i]-color[i])**2
    return diff



def grab(bbox = None):
    size, data = grabber()
    im = Image.frombytes("RGB", size, data, "raw", "BGR", (size[0]*3 + 3) & -4, -1)
    if bbox:
        im = im.crop(bbox)
    return im

def difcolor(target, image, size):
    (x, y) = image.size
    print(x, y)
    diff = 3

    i_desktop_window_id = win32gui.GetDesktopWindow()
    i_desktop_window_dc = win32gui.GetWindowDC(i_desktop_window_id)

    
    poss = [[(300+i,250+j) for i in range(0,500,diff)] for j in range(0,500,diff)]
    colors = [[None for i in poss[0]] for j in poss]

    for i in range(len(poss)):
        for j in range(len(poss[0])):

            colors[i][j] = image.getpixel(poss[i][j])

    minDif = 3*256
    minI = 0
    minJ = 0
    minCol = (0, 0, 0)
    for i in range(len(colors)):
        for j in range(len(colors[0])):
            if checkCol(target, colors[i][j]) < minDif:
                minDif = checkCol(target, colors[i][j])
                minI = i
                minJ = j
                minCol = colors[i][j]
                print(i, j, minDif)

    tempDif = ()
    t0 = time.clock()
    while True:
        diff = 0
        for i in range(-1, 2):
            for j in range(-1, 2):

                tempCol = GetPixel(i_desktop_window_dc, poss[minI][minJ][0]+3*i, poss[minI][minJ][1]+3*j)
                tempTuple = (tempCol % 256, (tempCol//256) % 256, tempCol//(256*256))
                # print(checkCol(colors[minI+2*i][minJ+2*j], tempTuple))
                if checkCol(colors[minI+1*i][minJ+1*j], tempTuple) > 10000:
                    diff += 1
                #print(colors[minI+i][minJ+j], tempTuple, tempCol)
        print(diff, tempTuple)
        if diff > 5 or (time.clock() - t0 > 20):
            break

    SetCursorPos(poss[minI][minJ])

    mouse_event(win32con.MOUSEEVENTF_LEFTDOWN,0,0)
    mouse_event(win32con.MOUSEEVENTF_LEFTUP,0,0)


grabber = Image.core.grabscreen

hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()
wsh = comclt.Dispatch("WScript.Shell")

i_desktop_window_id = win32gui.GetDesktopWindow()
i_desktop_window_dc = win32gui.GetWindowDC(i_desktop_window_id)


while not start:
    pythoncom.PumpWaitingMessages()

tempCol = GetPixel(i_desktop_window_dc, size[0], size[1])
tempTuple = (tempCol % 256, (tempCol//256) % 256, tempCol//(256*256))
print(tempTuple)

time.sleep(1)
baitTime = time.clock()
baited = False

while True:
    time.sleep(1.5)
    SetCursorPos((1000, 900))

    if (time.clock()-baitTime > 600):
        baited = False
        baitTime = time.clock()

    if (not baited):
        keybd_event(0x47, 0, ) # G
        time.sleep(.1)
        baited = True
        keybd_event(0x47, 0, 2) # G
    
    if (start or True):
        keybd_event(0x46, 0, ) # F

        start = False
        time.sleep(1.5)
        keybd_event(0x46, 0, 2) # F
        image = grab()
        
        difcolor(tempTuple, image, size)
        


