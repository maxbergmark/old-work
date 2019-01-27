from win32api import *
from win32gui import *
import time
from math import sin, cos, pi
import pyHook
import pythoncom

def callback(event):
    if event.Key == 'Q':
        quit()
    return True

hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()

while True:
    for i in range(0, 7*11*13*1000, 1):
        SetCursorPos((1230+int(300*sin(7*pi*i/1000)+300*sin(11*pi*i/1000)), 720+int(200*cos(13*pi*i/1000)+200*cos(17*pi*i/1000))))
        time.sleep(0.005)
        pythoncom.PumpWaitingMessages()
