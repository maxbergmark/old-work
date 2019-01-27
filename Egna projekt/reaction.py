import win32gui
import win32api
from win32con import *
import time


def checkColor(color):
	return color == win32gui.GetPixel(win32gui.GetDC(win32gui.GetActiveWindow()), 1000 , 1000)

test = True
while test:
	test = checkColor(2**24-1)

#time.sleep(.05)

win32api.mouse_event(MOUSEEVENTF_LEFTDOWN,0,0)
win32api.mouse_event(MOUSEEVENTF_LEFTUP,0,0)